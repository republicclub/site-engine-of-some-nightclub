package by.ladyka.club.service;

import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.EventTicketPriceType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ClubEventTicketPriceService {
    Optional<BigDecimal> getLowPriceForEventByPriceType(Long eventId, EventTicketPriceType eventTicketPriceType);
    ClubEventTicketPriceDTO save(ClubEventTicketPriceDTO requestDto, String username);
    List<ClubEventTicketPriceDTO> getAllByEventId(Long eventId, String username);
    BigDecimal getLowPriceForEventDance(EventEntity eventEntity);
    BigDecimal getLowPriceForEventTablePlace(EventEntity eventEntity);
}
