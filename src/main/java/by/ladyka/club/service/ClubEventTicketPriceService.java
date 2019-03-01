package by.ladyka.club.service;

import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.EventTicketPriceType;

import java.math.BigDecimal;
import java.util.Optional;

public interface ClubEventTicketPriceService {
    Optional<BigDecimal> getLowPriceForEventByPriceType(Long eventId, EventTicketPriceType eventTicketPriceType);
    BigDecimal getLowPriceForEventDance(EventEntity eventEntity);
    BigDecimal getLowPriceForEventTablePlace(EventEntity eventEntity);
}
