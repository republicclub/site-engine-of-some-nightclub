package by.ladyka.club.service;

import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.ClubEventTicketPrice;
import by.ladyka.club.entity.EventTicketPriceType;
import by.ladyka.club.repository.ClubEventTicketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ClubEventTicketPriceServiceImpl implements ClubEventTicketPriceService {

    @Autowired
    private ClubEventTicketPriceRepository clubEventTicketPriceRepository;

    @Override
    public Optional<BigDecimal> getLowPriceForEventByPriceType(Long eventId, EventTicketPriceType eventTicketPriceType) {
        List<ClubEventTicketPrice> eventTicketPrices = clubEventTicketPriceRepository.findAscSortPricesForEventByPriceType(eventId, eventTicketPriceType, PageRequest.of(0, 1));
        if(eventTicketPrices.size() > 0){
            return Optional.of(eventTicketPrices.get(0).getCost());
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
