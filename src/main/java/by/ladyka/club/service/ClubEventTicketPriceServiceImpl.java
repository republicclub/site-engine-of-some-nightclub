package by.ladyka.club.service;

import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.ClubEventTicketPrice;
import by.ladyka.club.entity.EventTicketPriceType;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.repository.ClubEventTicketPriceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.AccessControlException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubEventTicketPriceServiceImpl implements ClubEventTicketPriceService {

    @Autowired
    private ClubEventTicketPriceRepository clubEventTicketPriceRepository;


    @Autowired
    private EventsService eventsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConverterClubEventTicketPriceService converterClubEventTicketPriceService;

    @Override
    public List<ClubEventTicketPriceDTO> getAllByEventId(Long eventId, String username){
        UserEntity user = userService.getUserEntity(username);
        EventEntity event = eventsService.getEventById(eventId).orElseThrow(() -> new RuntimeException("impossible to find a prices for a non-existent event"));
        if(eventsService.hasAccess(event, user)){
            return event
                    .getTicketPrices()
                    .stream()
                    .map((entity) -> converterClubEventTicketPriceService.toDto(entity))
                    .collect(Collectors.toList());
        }else{
            throw new AccessControlException("This has't access of this event");
        }
    }

    @Override
    public ClubEventTicketPriceDTO save(ClubEventTicketPriceDTO requestDto, String username){
        UserEntity user = userService.getUserEntity(username);
        EventEntity event = eventsService.getEventById(requestDto.getEventId()).orElseThrow(() -> new RuntimeException("impossible to create a price for a non-existent event"));
        ClubEventTicketPrice clubEventTicketPrice;
        if(eventsService.hasAccess(event, user)){
            if (requestDto.getId() == null || requestDto.getId() < 1) {
                clubEventTicketPrice = new ClubEventTicketPrice();
                clubEventTicketPrice = converterClubEventTicketPriceService.toEntity(requestDto, clubEventTicketPrice, null);
            }else {
                clubEventTicketPrice = clubEventTicketPriceRepository.findById(requestDto.getId()).orElseGet(() -> {
                    final ClubEventTicketPrice clubEventTicketPriceNewed = new ClubEventTicketPrice();
                    converterClubEventTicketPriceService.toEntity(requestDto, clubEventTicketPriceNewed, null);
                    clubEventTicketPriceNewed.setId(requestDto.getId());
                    return clubEventTicketPriceNewed;
                });
                clubEventTicketPrice = converterClubEventTicketPriceService.toEntity(requestDto, clubEventTicketPrice, new String[]{"cost", "type"});
            }
            clubEventTicketPrice.setModifiedBy(user);
            clubEventTicketPrice = clubEventTicketPriceRepository.save(clubEventTicketPrice);
            return converterClubEventTicketPriceService.toDto(clubEventTicketPrice);
        }else{
            throw new AccessControlException("This has't access of this event");
        }
    }

    @Override
    public Optional<BigDecimal> getLowPriceForEventByPriceType(Long eventId, EventTicketPriceType eventTicketPriceType) {
        Page<ClubEventTicketPrice> eventTicketPrices = clubEventTicketPriceRepository.findAscSortPricesForEventByPriceType(eventId, eventTicketPriceType, PageRequest.of(0, 1));
        if(eventTicketPrices.getTotalElements() > 0){
            return Optional.of(eventTicketPrices.getContent().get(0).getCost());
        }else{
            return Optional.empty();
        }

    }

	@Override
	public BigDecimal getLowPriceForEventDance(EventEntity event) {
		return getLowPriceForEventByPriceType(event.getId(), EventTicketPriceType.dance).orElse(BigDecimal.valueOf(0L));
	}

	@Override
	public BigDecimal getLowPriceForEventTablePlace(EventEntity event) {
		return getLowPriceForEventByPriceType(event.getId(), EventTicketPriceType.table).orElse(BigDecimal.valueOf(0L));
	}
}
