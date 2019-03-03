package by.ladyka.club.service;

import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.entity.ClubEventTicketPrice;
import by.ladyka.club.entity.EventEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConverterClubEventTicketPriceServiceImpl implements ConverterClubEventTicketPriceService{

    @Autowired
    private EventsService eventsService;

    @Override
    public ClubEventTicketPriceDTO toDto(ClubEventTicketPrice entity) {
        ClubEventTicketPriceDTO dto = new ClubEventTicketPriceDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setEventId(entity.getEvent().getId());
        return dto;
    }

    @Override
    public ClubEventTicketPrice toEntity(ClubEventTicketPriceDTO dto, ClubEventTicketPrice targetEntity, String[] ignoreProperties) {
        BeanUtils.copyProperties(dto, targetEntity, ignoreProperties);
        EventEntity event = eventsService.getEventById(dto.getEventId()).orElseThrow(() -> new RuntimeException("could not find ClubEvent with current eventId"));
        targetEntity.setEvent(event);
        event.getTicketPrices().add(targetEntity);
        return targetEntity;
    }
}
