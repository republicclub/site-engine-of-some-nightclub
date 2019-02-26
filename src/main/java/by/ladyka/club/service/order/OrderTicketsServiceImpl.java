package by.ladyka.club.service.order;

import by.ladyka.bepaid.BePaidApi;
import by.ladyka.bepaid.dto.GatewayStatus;
import by.ladyka.bepaid.dto.PaymentTokenDto;
import by.ladyka.club.config.ClubRole;
import by.ladyka.club.config.CustomSettings;
import by.ladyka.club.dto.menu.TicketOrderDto;
import by.ladyka.club.dto.report.TicketOrderReportDto;
import by.ladyka.club.dto.tikets.*;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.entity.menu.MenuItemPricesHasOrders;
import by.ladyka.club.entity.order.OrderEntity;
import by.ladyka.club.entity.order.OrderItemEntity;
import by.ladyka.club.entity.order.TicketType;
import by.ladyka.club.repository.OrderEntityRepository;
import by.ladyka.club.repository.OrderItemEntityRepository;
import by.ladyka.club.service.EventsService;
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

import static by.ladyka.club.config.Constants.API_ORDER_BEPAID;

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
		orderItemEntityRepository.findByOrderEntityEventEntityId(eventId).forEach(orderItemEntity -> {
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
		OrderEntity orderEntity = storeOrder(dto, username);
		PaymentTokenDto paymentTokenDto = getPaymentTokenDto(orderEntity);
		final String token = paymentTokenDto.getCheckout().getToken();
		orderEntity.setToken(token);
		orderEntityRepository.save(orderEntity);
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

	private void splitOrderSendEmails(OrderEntity orderEntity) {
		final int dance = orderEntity.getDance();
		for (int i = 0; i < dance; i++) {
			OrderEntity danceTicket = new OrderEntity(orderEntity);
			danceTicket.setNewUuid();
			danceTicket.setDance(1);
			danceTicket.setTableNumbers(Collections.emptyList());
			danceTicket.setItemPricesHasOrders(Collections.emptyList());
			danceTicket.setTicketType(TicketType.TICKET);
			danceTicket = orderEntityRepository.save(danceTicket);
			emailService.sendOrderToOwner(danceTicket);
		}

		final List<OrderItemEntity> tableNumbers = orderEntity.getTableNumbers();
		tableNumbers.forEach(tablePlace -> {
			OrderEntity tableTicket = new OrderEntity(orderEntity);
			tableTicket.setNewUuid();
			tableTicket.setDance(0);
			tableTicket.setTableNumbers(Collections.emptyList());
			tableTicket.setItemPricesHasOrders(Collections.emptyList());
			tableTicket.setTicketType(TicketType.TICKET);
			tableTicket = orderEntityRepository.save(tableTicket);

			OrderItemEntity orderItemEntity = new OrderItemEntity();
			orderItemEntity.setOrderEntity(tableTicket);
			orderItemEntity.setPlace(tablePlace.getPlace());
			orderItemEntity.setTableNumber(tablePlace.getTableNumber());
			orderItemEntityRepository.save(orderItemEntity);

			tableTicket.setTableNumbers(Collections.singletonList(orderItemEntity));

			emailService.sendOrderToOwner(tableTicket);
		});
	}

	@Override
	public TicketOrderDto getOrder(String uuid) {

		final OrderEntity orderEntity = orderEntityRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("UUID is invalid"));
		return orderEntityConverter.toDto(orderEntity, true);

	}

	@Override
	public EventTicketsReportDto getReport(Long eventId) {
		EventTicketsReportDto reportDto = new EventTicketsReportDto();
		final List<OrderEntity> orders = orderEntityRepository.findAllByEventEntityIdAndTicketType(eventId, TicketType.TICKET);
		int dc = orders
				.stream()
				.map(OrderEntity::getDance)
				.mapToInt(Integer::intValue)
				.sum();
		reportDto.setDanceCount(dc);
		int tpc = orders
				.stream()
				.map(OrderEntity::getTableNumbers)
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
		List<OrderEntity> tickets = Collections.emptyList();
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
		final OrderEntity orderEntity = orderEntityRepository.findByUuid(uuid).orElseThrow(EntityNotFoundException::new);
		if (orderEntity.getEnterTime() != null) {
			throw new RuntimeException("Билет уже активирован!");
		}
		orderEntity.setEnterTime(LocalDateTime.now());
		orderEntity.setAcceptor(userEntity);
		orderEntityRepository.save(orderEntity);
		return true;
	}

	@Override
	public TicketOrderReportDto getOrderReport(String uuid) {
		final OrderEntity orderEntity = orderEntityRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("UUID is invalid"));
		final TicketOrderDto ticketOrderDto = orderEntityConverter.toDto(orderEntity, true);
		TicketOrderReportDto order = new TicketOrderReportDto(ticketOrderDto);
		String coverUri = orderEntity.getEventEntity().getCoverUri();
		if (coverUri.indexOf("/file") == 0) {
			coverUri = customSettings.getSiteDomain() + coverUri;
		}
		order.setEventImageUrl(coverUri);
		return order;
	}

	private OrderEntity storeOrder(@Valid TicketsOrderDto dto, String username) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setName(dto.getName());
		orderEntity.setSurname(dto.getSurname());
		orderEntity.setEmail(dto.getEmail());
		orderEntity.setPhone(dto.getPhone());
		orderEntity.setDescription(dto.getDescription());
		orderEntity.setDance(dto.getDanceFloor());
		final List<OrderItemEntity> collect = dto.getTables()
				.stream().map(ticketTableDto -> {
					final int tableNumber = ticketTableDto.getTableNumber();
					return ticketTableDto.getPlaces().stream()
							.filter(place -> TicketPlaceStatus.BOOKING.equals(place.getStatus()))
							.map(place -> {
								OrderItemEntity orderItemEntity = new OrderItemEntity();
								orderItemEntity.setOrderEntity(orderEntity);
								orderItemEntity.setTableNumber(tableNumber);
								orderItemEntity.setPlace(place.getPlaceNumber());
								return orderItemEntity;
							}).collect(Collectors.toList());
				})
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		orderEntity.setTableNumbers(collect);

		List<MenuItemPricesHasOrders> items = new ArrayList<>();
		orderEntity.setItemPricesHasOrders(items);
		final EventEntity eventEntity = eventService.getEventById(dto.getEvent().getId()).orElseThrow(RuntimeException::new);
		orderEntity.setEventEntity(eventEntity);
		orderEntity.setTotalOrder(amount(dto.getDanceFloor(), collect.size(), eventEntity.getCostDance(), eventEntity.getCostTablePlace()));

		if (username != null) {
			UserEntity bookUser = userService.getUserEntity(username);
			orderEntity.setBookUser(bookUser);
		}
		orderEntity.setTicketType(TicketType.ORDER);
		orderEntityRepository.saveAndFlush(orderEntity);
		orderItemEntityRepository.saveAll(collect);
		return orderEntity;
	}

	private PaymentTokenDto getPaymentTokenDto(OrderEntity orderEntity) {
		final String requestId = OrderEntity.class.getName() + orderEntity.getUuid();
		final String redirectUrl = String.format("%s" + API_ORDER_BEPAID + "/callback/%s", customSettings.getSiteDomain(), orderEntity.getUuid());
		PaymentTokenDto paymentTokenDto = bePaidApi.getPaymentTokenDto(
				amount(orderEntity),
				orderEntity.getEmail(),
				orderEntity.getSurname(),
				orderEntity.getName(),
				orderEntity.getPhone(),
				redirectUrl,
				customSettings.getBePaidPaymentTest(),
				String.format("Оплата заказа №%d | RE:Public Club", orderEntity.getId())
		);
		try {
			paymentTokenDto = bePaidApi.getOrderToken(paymentTokenDto, requestId);
		} catch (IOException | URISyntaxException | NoSuchAlgorithmException ex) {
			logger.error("FAIL !!!!", ex);
			throw new RuntimeException(ex);
		}
		return paymentTokenDto;
	}

	private long amount(OrderEntity orderEntity) {
		final EventEntity eventEntity = orderEntity.getEventEntity();
		final BigDecimal costDance = eventEntity.getCostDance();
		final BigDecimal costTablePlace = eventEntity.getCostTablePlace();
		return amount(orderEntity.getDance(), orderEntity.getTableNumbers().size(), costDance, costTablePlace).longValue() * 100;
	}


	private BigDecimal amount(int dance, int table, final BigDecimal costDance, final BigDecimal costTablePlace) {
		return costDance.multiply(new BigDecimal(dance)).add(costTablePlace.multiply(new BigDecimal(table)));
	}
}
