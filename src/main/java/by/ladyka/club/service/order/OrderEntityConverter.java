package by.ladyka.club.service.order;

import by.ladyka.club.dto.EventDTO;
import by.ladyka.club.dto.menu.TicketOrderDto;
import by.ladyka.club.dto.tikets.*;
import by.ladyka.club.entity.order.OrderItemEntity;
import by.ladyka.club.entity.order.TicketOrderEntity;
import by.ladyka.club.service.ConverterEventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderEntityConverter {

	@Autowired
	ConverterEventService converterEventService;

	public TicketOrderDto toDto(TicketOrderEntity entity, boolean deps) {
		TicketOrderDto dto = new TicketOrderDto();
		BeanUtils.copyProperties(entity, dto);
		dto.setEventName(entity.getEventEntity().getName());
		if (entity.getPayStatus() != null) {
			dto.setPayStatus(entity.getPayStatus().name());
		} else {
			dto.setPayStatus("Не оплачено, завершите оплату!");
		}
		dto.setTotalMoney(entity.getTotalOrder());
		if (entity.getEnterTime() != null) {
			dto.setAcceptor(entity.getAcceptor().getPublishName());
			dto.setEnterTime(entity.getEnterTime());
		}
		if (deps) {
			dto.setTables(entity.getTableNumbers()
					.stream()
					.map(ent -> new TablePlaceDto(ent.getTableNumber(), ent.getPlace()))
					.collect(Collectors.toList()));
		}
		return dto;
	}

	public TicketsOrderDto toTicketsOrderDto(TicketOrderEntity entity, boolean deps) {
		TicketsOrderDto dto = new TicketsOrderDto();
		BeanUtils.copyProperties(entity, dto);

		dto.setId(entity.getId());
		dto.setUuid(entity.getUuid());
		dto.setPayStatus(entity.getPayStatus().name());
		dto.setDanceFloor(entity.getDance());
		if (entity.getEnterTime() != null) {
			dto.setEnterTime(entity.getEnterTime());
			dto.setAcceptor(entity.getAcceptor().getPublishName());
		}

		if (deps) {
			final EventDTO eventDto = converterEventService.toEventDto(entity.getEventEntity());
			dto.setEvent(eventDto);
			dto.setStart(eventDto.getStartEvent());

			final List<OrderItemEntity> tableNumbers = entity.getTableNumbers();
			Map<Integer, TicketTableDto> tableDtoMap = new HashMap<>();
			tableNumbers.forEach(orderItemEntity -> {
				final int tableNumber = orderItemEntity.getTableNumber();
				TicketTableDto ticketTableDto = tableDtoMap.get(tableNumber);
				if (ticketTableDto == null) {
					ticketTableDto = new TicketTableDto();
					ticketTableDto.setTableNumber(tableNumber);
				}
				TicketPlaceDto e = new TicketPlaceDto();
				e.setPlaceNumber(orderItemEntity.getPlace());
				e.setStatus(TicketPlaceStatus.BUSY);
				ticketTableDto.getPlaces().add(e);
				tableDtoMap.put(tableNumber, ticketTableDto);
			});
			dto.setTables(new ArrayList<>(tableDtoMap.values()));
		}
		return dto;
	}

	public List<TicketsOrderDto> toTicketsOrderDtos(List<TicketOrderEntity> tickets, boolean deps) {
		return tickets.stream().map(t -> toTicketsOrderDto(t, deps)).collect(Collectors.toList());
	}
}
