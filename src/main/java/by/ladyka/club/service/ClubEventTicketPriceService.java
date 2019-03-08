package by.ladyka.club.service;

import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.entity.ClubEventTicketPrice;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.EventTicketPriceType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ClubEventTicketPriceService {
    Optional<ClubEventTicketPrice> getLowPriceForEventByPriceType(Long eventId, EventTicketPriceType eventTicketPriceType);
    ClubEventTicketPriceDTO save(ClubEventTicketPriceDTO requestDto, String username);
    List<ClubEventTicketPriceDTO> getAllByEventId(Long eventId, String username);
    Optional<ClubEventTicketPrice> getLowPriceEntityForEventDance(EventEntity event);
    Optional<ClubEventTicketPrice> getLowPriceEntityForEventTablePlace(EventEntity event);
    BigDecimal getLowPriceForEventDance(EventEntity eventEntity);
    BigDecimal getLowPriceForEventTablePlace(EventEntity eventEntity);
}
