package by.ladyka.club.service;

import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.entity.ClubEventTicketPrice;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.EventTicketPriceType;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.repository.ClubEventTicketPriceRepository;
import by.ladyka.club.repository.OrderEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.security.AccessControlException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubEventTicketPriceServiceImpl implements ClubEventTicketPriceService {

	@Autowired
	private ClubEventTicketPriceRepository clubEventTicketPriceRepository;

	@Autowired
	private OrderEntityRepository orderEntityRepository;

	@Autowired
	private EventsService eventsService;

	@Autowired
	private UserService userService;

	@Autowired
	private ConverterClubEventTicketPriceService converterClubEventTicketPriceService;

	@Override
	public List<ClubEventTicketPriceDTO> getAllByEventId(Long eventId, String username) {
		UserEntity user = userService.getUserEntity(username);
		EventEntity event = eventsService.getEventById(eventId).orElseThrow(() -> new RuntimeException("impossible to find a prices for a non-existent event"));
		if (eventsService.hasAccess(event, user)) {
			return event
					.getTicketPrices()
					.stream()
					.map((entity) -> converterClubEventTicketPriceService.toDto(entity))
					.collect(Collectors.toList());
		} else {
			throw new AccessControlException("This has't access of this event");
		}
	}

	@Override
	public ClubEventTicketPriceDTO save(ClubEventTicketPriceDTO requestDto, String username) {
		UserEntity user = userService.getUserEntity(username);
		EventEntity event = eventsService.getEventById(requestDto.getEventId()).orElseThrow(() -> new RuntimeException("impossible to create a price for a non-existent event"));
		ClubEventTicketPrice clubEventTicketPrice;
		checkAccessUserToEvent(event, user);
		if (requestDto.getId() == null || requestDto.getId() < 1) {
			clubEventTicketPrice = createNewPrice(requestDto);
		} else {
			Optional<ClubEventTicketPrice> clubEventTicketPriceOptional = clubEventTicketPriceRepository.findById(requestDto.getId());
			if (clubEventTicketPriceOptional.isPresent()) {
				clubEventTicketPrice = changePrice(requestDto, clubEventTicketPriceOptional.get());
			} else {
				clubEventTicketPrice = createNewPriceWithParticularId(requestDto);
			}

		}
		clubEventTicketPrice.setModifiedBy(user);
		clubEventTicketPrice = clubEventTicketPriceRepository.save(clubEventTicketPrice);
		return converterClubEventTicketPriceService.toDto(clubEventTicketPrice);
	}

	private void checkAccessUserToEvent(EventEntity event, UserEntity user) {
		if (!eventsService.hasAccess(event, user)) {
			throw new AccessControlException("This has't access of this event");
		}
	}

	private ClubEventTicketPrice createNewPriceWithParticularId(ClubEventTicketPriceDTO requestDto) {
		final ClubEventTicketPrice clubEventTicketPriceNewed = new ClubEventTicketPrice();
		converterClubEventTicketPriceService.toEntity(requestDto, clubEventTicketPriceNewed, null);
		clubEventTicketPriceNewed.setId(requestDto.getId());
		return clubEventTicketPriceNewed;
	}

	private ClubEventTicketPrice createNewPrice(ClubEventTicketPriceDTO requestDto) {
		ClubEventTicketPrice clubEventTicketPrice = new ClubEventTicketPrice();
		clubEventTicketPrice = converterClubEventTicketPriceService.toEntity(requestDto, clubEventTicketPrice, null);
		return clubEventTicketPrice;
	}

	private ClubEventTicketPrice changePrice(ClubEventTicketPriceDTO requestDto, ClubEventTicketPrice clubEventTicketPrice) {
		if (orderEntityRepository.countByClubEventTicketPrice(clubEventTicketPrice) > 0) {
			clubEventTicketPrice = converterClubEventTicketPriceService.toEntity(requestDto, clubEventTicketPrice, new String[]{"cost", "type"});
		} else {
			clubEventTicketPrice = converterClubEventTicketPriceService.toEntity(requestDto, clubEventTicketPrice, null);
		}
		return clubEventTicketPrice;
	}

	public Optional<ClubEventTicketPrice> getLowPriceForEventByPriceType(EventEntity event, EventTicketPriceType eventTicketPriceType) {
		return clubEventTicketPriceRepository.findTop1ByStartActiveTimeIsBeforeAndEndActiveTimeAfterAndEventAndTypeOrderByCost(
				LocalDateTime.now(),
				LocalDateTime.now(),
				event,
				eventTicketPriceType
		);
	}

	@Override
	public boolean delete(Long priceId, String username) {
		final UserEntity userEntity = userService.getUserEntity(username);
		final ClubEventTicketPrice price = clubEventTicketPriceRepository.findById(priceId).orElseThrow(EntityNotFoundException::new);
		price.setModifiedBy(userEntity);
		price.setVisible(Boolean.FALSE);
		clubEventTicketPriceRepository.save(price);
		return true;
	}

	@Override
	public BigDecimal getLowPriceForEventDance(EventEntity event) {
		Optional<ClubEventTicketPrice> clubEventTicketPriceOptional = getLowPriceForEventByPriceType(event, EventTicketPriceType.dance);
		if (clubEventTicketPriceOptional.isPresent()) {
			return clubEventTicketPriceOptional.get().getCost();
		} else {
			return BigDecimal.valueOf(0L);
		}
	}

	@Override
	public BigDecimal getLowPriceForEventTablePlace(EventEntity event) {
		Optional<ClubEventTicketPrice> clubEventTicketPriceOptional = getLowPriceForEventByPriceType(event, EventTicketPriceType.table);
		if (clubEventTicketPriceOptional.isPresent()) {
			return clubEventTicketPriceOptional.get().getCost();
		} else {
			return BigDecimal.valueOf(0L);
		}
	}
}
