package by.ladyka.club.service;

import by.ladyka.club.dto.EventDTO;
import by.ladyka.club.dto.EventRelevantDTO;
import by.ladyka.club.entity.EventEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConverterEventServiceImpl implements ConverterEventService {
	@Autowired
	private ClubEventTicketPriceService clubEventTicketPriceService;

	@Override
	public EventDTO toEventDto(EventEntity entity) {
		EventDTO eventDTO = new EventDTO();
		BeanUtils.copyProperties(entity, eventDTO);
		eventDTO.setCostDance(clubEventTicketPriceService.getLowPriceForEventDance(entity));
		eventDTO.setCostTablePlace(clubEventTicketPriceService.getLowPriceForEventTablePlace(entity));
		return eventDTO;
	}

	@Override
	public EventRelevantDTO toEventRelevantDto(EventEntity eventEntity) {
		EventRelevantDTO dto = new EventRelevantDTO();
		dto.setId(eventEntity.getId());
		dto.setCoverUri(eventEntity.getCoverUri());
		dto.setName(eventEntity.getName());
		dto.setStartEvent(eventEntity.getStartEvent());
		return dto;
	}

	public EventEntity toEntity(EventDTO dto, EventEntity target) {
		BeanUtils.copyProperties(dto,target);
		return target;
	}

}
