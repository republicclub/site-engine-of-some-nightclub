package by.ladyka.club.service.order;

import by.ladyka.bepaid.BePaidApi;
import by.ladyka.bepaid.dto.GatewayStatus;
import by.ladyka.bepaid.dto.PaymentTokenDto;
import by.ladyka.club.config.constant.ClubRole;
import by.ladyka.club.config.CustomSettings;
import by.ladyka.club.dto.menu.TicketOrderDto;
import by.ladyka.club.dto.report.TicketOrderReportDto;
import by.ladyka.club.dto.tikets.*;
import by.ladyka.club.entity.ClubEventTicketPrice;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.EventTicketPriceType;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.entity.order.TicketOrderEntity;
import by.ladyka.club.entity.order.OrderItemEntity;
import by.ladyka.club.entity.order.TicketType;
import by.ladyka.club.repository.OrderEntityRepository;
import by.ladyka.club.repository.OrderItemEntityRepository;
import by.ladyka.club.service.ClubEventTicketPriceService;
import by.ladyka.club.service.EventsService;
import by.ladyka.club.service.TicketKitchenService;
import by.ladyka.club.service.UserService;
import by.ladyka.club.service.email.EmailService;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.AccessControlException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static by.ladyka.club.config.constant.Constants.API_ORDER_BEPAID;

@Service
public class OrderTicketsServiceImpl implements OrderTicketsService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	UserService userService;
	@Autowired
	private OrderItemEntityRepository orderItemEntityRepository;
	@Autowired
	private EventsService eventService;
	@Autowired
	private OrderEntityRepository orderEntityRepository;
	@Autowired
	private BePaidApi bePaidApi;
	@Autowired
	private CustomSettings customSettings;
	@Autowired
	private OrderEntityConverter orderEntityConverter;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ClubEventTicketPriceService clubEventTicketPriceService;
	@Autowired
	private TicketKitchenService ticketKitchenService;

	@Override
	public List<TicketTableDto> getTables(Long eventId) {
		List<TicketTableDto> tables = new ArrayList<>();
		for (int table = 1; table < 26; table++) {
			TicketTableDto ticketTableDto = new TicketTableDto();
			ticketTableDto.setTableNumber(table);
			List<TicketPlaceDto> places = new ArrayList<>();
			for (int placeNumber = 1; placeNumber < 5; placeNumber++) {
				TicketPlaceDto place = new TicketPlaceDto();
				place.setStatus(TicketPlaceStatus.FREE);
				place.setPlaceNumber(placeNumber);
				places.add(place);
			}
			ticketTableDto.setPlaces(places);
			tables.add(ticketTableDto);
		}
		orderItemEntityRepository.findByTicketOrderEntityEventEntityId(eventId).forEach(orderItemEntity -> {
			for (TicketTableDto table : tables) {
				if (table.getTableNumber() == orderItemEntity.getTableNumber()) {
					List<TicketPlaceDto> places = table.getPlaces();
					for (TicketPlaceDto place : places) {
						if (place.getPlaceNumber() == orderItemEntity.getPlace()) {
							place.setStatus(TicketPlaceStatus.BUSY);
						}
					}
				}
			}
		});
		return tables;
	}

	@Override
	public String bookAndPay(@Valid TicketsOrderDto dto, String username) {
		TicketOrderEntity ticketOrderEntity = storeOrder(dto, username);
		PaymentTokenDto paymentTokenDto = getPaymentTokenDto(ticketOrderEntity);
		final String token = paymentTokenDto.getCheckout().getToken();
		ticketOrderEntity.setToken(token);
		orderEntityRepository.save(ticketOrderEntity);
		return paymentTokenDto.getCheckout().getRedirectUrl();
	}

	@Override
	public boolean updateStatus(String uuid, GatewayStatus gatewayStatus, String uid, String token) {
		try {
			orderEntityRepository.findByUuid(uuid).ifPresent(orderEntity -> {
				if (StringUtils.equals(orderEntity.getToken(), token)) {
					orderEntity.setPayStatus(gatewayStatus);
					orderEntity.setUid(uid);
					orderEntityRepository.save(orderEntity);
					splitOrderSendEmails(orderEntity);
				} else {
					throw new RuntimeException("Token is invalid");
				}
			});
			return true;
		} catch (RuntimeException ex) {
			logger.error("FAIL", ex);
			return false;
		}

	}

	private void splitOrderSendEmails(TicketOrderEntity ticketOrderEntity) {
		final Long dance = ticketOrderEntity.getDance();
		for (int i = 0; i < dance; i++) {
			TicketOrderEntity danceTicket = new TicketOrderEntity(ticketOrderEntity);
			danceTicket.setNewUuid();
			danceTicket.setDance(1L);
			danceTicket.setTableNumbers(Collections.emptyList());
			danceTicket.setItemPricesHasOrders(Collections.emptyList());
			danceTicket.setTicketType(TicketType.TICKET);
			danceTicket = orderEntityRepository.save(danceTicket);
			emailService.sendOrderToOwner(danceTicket);
		}

		final List<OrderItemEntity> tableNumbers = ticketOrderEntity.getTableNumbers();
		tableNumbers.forEach(tablePlace -> {
			TicketOrderEntity tableTicket = new TicketOrderEntity(ticketOrderEntity);
			tableTicket.setNewUuid();
			tableTicket.setDance(0L);
			tableTicket.setTableNumbers(Collections.emptyList());
			tableTicket.setItemPricesHasOrders(Collections.emptyList());
			tableTicket.setTicketType(TicketType.TICKET);
			tableTicket = orderEntityRepository.save(tableTicket);

			OrderItemEntity orderItemEntity = new OrderItemEntity();
			orderItemEntity.setTicketOrderEntity(tableTicket);
			orderItemEntity.setPlace(tablePlace.getPlace());
			orderItemEntity.setTableNumber(tablePlace.getTableNumber());
			orderItemEntityRepository.save(orderItemEntity);

			tableTicket.setTableNumbers(Collections.singletonList(orderItemEntity));

			emailService.sendOrderToOwner(tableTicket);
		});
	}

	@Override
	public TicketOrderDto getOrder(String uuid) {

		final TicketOrderEntity ticketOrderEntity = orderEntityRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("UUID is invalid"));
		return orderEntityConverter.toDto(ticketOrderEntity, true);

	}

	@Override
	public EventTicketsReportDto getReport(Long eventId) {
		EventTicketsReportDto reportDto = new EventTicketsReportDto();
		final List<TicketOrderEntity> orders = orderEntityRepository.findAllByEventEntityIdAndTicketType(eventId, TicketType.TICKET);
		Long dc = orders
				.stream()
				.map(TicketOrderEntity::getDance)
				.mapToLong(Long::longValue)
				.sum();
		reportDto.setDanceCount(dc);
		int tpc = orders
				.stream()
				.map(TicketOrderEntity::getTableNumbers)
				.map(List::size)
				.mapToInt(Integer::intValue)
				.sum();
		reportDto.setTablePlacesCount(tpc);
		return reportDto;
	}

	@Override
	public List<TicketsOrderDto> getTickets(String username, Long eventId, String filter) {
		UserEntity userEntity = userService.getUserEntity(username);
		EventEntity event = eventService.getEventById(eventId).orElseThrow(EntityNotFoundException::new);
		List<TicketOrderEntity> tickets = Collections.emptyList();
		final String role = userService.getRole(userEntity);
		switch (role) {
			case ClubRole.ROLE_CONCERT: {
				if (!eventService.hasAccess(event, userEntity)) {
					throw new AccessControlException(String.format("User %s hasn't access to this event %d", username, eventId));
				}
			}
			case ClubRole.ROLE_ADMIN: {
				tickets = orderEntityRepository.findTop5ByEventEntityIdAndUuidContains(eventId, filter);
			}
			break;
			default: {

			}

		}

		return orderEntityConverter.toTicketsOrderDtos(tickets, true);
	}

	@Override
	public Boolean acceptTicket(String username, String uuid) {
		UserEntity userEntity = userService.getUserEntity(username);
		final TicketOrderEntity ticketOrderEntity = orderEntityRepository.findByUuid(uuid).orElseThrow(EntityNotFoundException::new);
		if (ticketOrderEntity.getEnterTime() != null) {
			throw new RuntimeException("Билет уже активирован!");
		}
		ticketOrderEntity.setEnterTime(LocalDateTime.now());
		ticketOrderEntity.setAcceptor(userEntity);
		orderEntityRepository.save(ticketOrderEntity);
		return true;
	}

	@Override
	public TicketOrderReportDto getOrderReport(String uuid) {
		final TicketOrderEntity ticketOrderEntity = orderEntityRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("UUID is invalid"));
		final TicketOrderDto ticketOrderDto = orderEntityConverter.toDto(ticketOrderEntity, true);
		TicketOrderReportDto order = new TicketOrderReportDto(ticketOrderDto);
		String coverUri = ticketOrderEntity.getEventEntity().getCoverUri();
		if (coverUri.indexOf("/file") == 0) {
			coverUri = customSettings.getSiteDomain() + coverUri;
		}
		order.setEventImageUrl(coverUri);
		return order;
	}

	private TicketOrderEntity storeOrder(@Valid TicketsOrderDto dto, String username) {
		TicketOrderEntity ticketOrderEntity = new TicketOrderEntity();
		ticketOrderEntity.setName(dto.getName());
		ticketOrderEntity.setSurname(dto.getSurname());
		ticketOrderEntity.setEmail(dto.getEmail());
		ticketOrderEntity.setPhone(dto.getPhone());
		ticketOrderEntity.setDescription(dto.getDescription());
		ticketOrderEntity.setDance(dto.getDanceFloor());
		final List<OrderItemEntity> orderItems = dto.getTables()
				.stream().map(ticketTableDto -> {
					final int tableNumber = ticketTableDto.getTableNumber();
					return ticketTableDto.getPlaces().stream()
							.filter(place -> TicketPlaceStatus.BOOKING.equals(place.getStatus()))
							.map(place -> {
								OrderItemEntity orderItemEntity = new OrderItemEntity();
								orderItemEntity.setTicketOrderEntity(ticketOrderEntity);
								orderItemEntity.setTableNumber(tableNumber);
								orderItemEntity.setPlace(place.getPlaceNumber());
								return orderItemEntity;
							}).collect(Collectors.toList());
				})
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		ticketOrderEntity.setTableNumbers(orderItems);

		final EventEntity eventEntity = eventService.getEventById(dto.getEvent().getId()).orElseThrow(RuntimeException::new);
		ticketOrderEntity.setEventEntity(eventEntity);

		if(dto.getDanceFloor() > 0){
            ClubEventTicketPrice clubEventTicketPriceDance = clubEventTicketPriceService.getLowPriceForEventByPriceType(eventEntity, EventTicketPriceType.dance)
                    .orElseThrow(() -> new RuntimeException("tickets are no longer sold"));
            if(clubEventTicketPriceDance.getCost().compareTo(dto.getEvent().getCostDance()) != 0){
                throw new RuntimeException("tickets at this price is no longer on sale");
            }
			ticketOrderEntity.setClubEventTicketPrice(clubEventTicketPriceDance);
        }

        if(orderItems.size() > 0){
            ClubEventTicketPrice clubEventTicketPriceTable = clubEventTicketPriceService.getLowPriceForEventByPriceType(eventEntity, EventTicketPriceType.table)
                    .orElseThrow(() -> new RuntimeException("tickets are no longer sold"));
            if(clubEventTicketPriceTable.getCost().compareTo(dto.getEvent().getCostTablePlace()) != 0){
                throw new RuntimeException("tickets at this price is no longer on sale");
            }
            ticketOrderEntity.setClubEventTicketPrice(clubEventTicketPriceTable);
        }

		ticketOrderEntity.setTotalOrder(amount(ticketOrderEntity));

		if (username != null) {
			UserEntity bookUser = userService.getUserEntity(username);
			ticketOrderEntity.setBookUser(bookUser);
		}
		ticketOrderEntity.setTicketType(TicketType.ORDER);
		orderEntityRepository.saveAndFlush(ticketOrderEntity);
		ticketKitchenService.storeMenuItems(dto, ticketOrderEntity);
		orderItemEntityRepository.saveAll(orderItems);
		return ticketOrderEntity;
	}

	private PaymentTokenDto getPaymentTokenDto(TicketOrderEntity ticketOrderEntity) {
		final String requestId = TicketOrderEntity.class.getName() + ticketOrderEntity.getUuid();
		final String redirectUrl = String.format("%s" + API_ORDER_BEPAID + "/callback/%s", customSettings.getSiteDomain(), ticketOrderEntity.getUuid());
		PaymentTokenDto paymentTokenDto = bePaidApi.getPaymentTokenDto(
				amount(ticketOrderEntity),
				ticketOrderEntity.getEmail(),
				ticketOrderEntity.getSurname(),
				ticketOrderEntity.getName(),
				ticketOrderEntity.getPhone(),
				redirectUrl,
				customSettings.getBePaidPaymentTest(),
				String.format("Оплата заказа №%d | RE:Public Club", ticketOrderEntity.getId())
		);
		try {
			paymentTokenDto = bePaidApi.getOrderToken(paymentTokenDto, requestId);
		} catch (IOException | URISyntaxException | NoSuchAlgorithmException ex) {
			logger.error("FAIL !!!!", ex);
			throw new RuntimeException(ex);
		}
		return paymentTokenDto;
	}

	private BigDecimal amount(TicketOrderEntity ticketOrderEntity) {
		final EventEntity eventEntity = ticketOrderEntity.getEventEntity();
		final BigDecimal costDance = clubEventTicketPriceService.getLowPriceForEventDance(eventEntity);
		final BigDecimal costTablePlace = clubEventTicketPriceService.getLowPriceForEventTablePlace(eventEntity );

		long danceCount = ticketOrderEntity.getDance();
		int tablePlaces = ticketOrderEntity.getTableNumbers().size();
		BigDecimal amount = costDance.multiply(new BigDecimal(danceCount)).add(costTablePlace.multiply(new BigDecimal(tablePlaces)));
		amount = amount.add(ticketKitchenService.amount(ticketOrderEntity));
		return amount;
	}
}
