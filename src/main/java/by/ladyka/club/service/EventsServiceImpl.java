package by.ladyka.club.service;

import by.ladyka.club.config.constant.ClubRole;
import by.ladyka.club.dto.AppUser;
import by.ladyka.club.dto.EventDTO;
import by.ladyka.club.dto.EventRelevantDTO;
import by.ladyka.club.entity.AbstractEntity;
import by.ladyka.club.entity.AuthorityEntity;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.AccessControlException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

@Service
public class EventsServiceImpl implements EventsService {

	@Autowired
	UserService userService;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private ConverterEventService converterEventService;

	@Override
	public List<EventDTO> getEventsBetween(LocalDateTime after, LocalDateTime before) {
		return eventRepository
				.findAllByStartEventBetweenAndVisibleTrue(after, before)
				.stream()
				.map(converterEventService::toEventDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<EventDTO> getEventsAfter(LocalDateTime time) {
		return eventRepository
				.findAllByStartEventGreaterThanAndVisibleTrueOrderByStartEventAsc(time)
				.stream()
				.map(converterEventService::toEventDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<EventDTO> getEventsAfterAndRepublicPayTrue(LocalDateTime time) {
		return eventRepository
				.findAllByStartEventGreaterThanAndVisibleTrueAndRepublicPayTrueOrderByStartEventAsc(time)
				.stream()
				.map(converterEventService::toEventDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<EventRelevantDTO> getRelevantEvents(AppUser user) {
		return eventRepository
				.findByRecommendationAndStartEventGreaterThanAndVisibleTrueOrderByStartEventAsc(TRUE, LocalDateTime.now().minusHours(5L))
				.stream()
				.map(converterEventService::toEventRelevantDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<EventDTO> getEvents(String sort, String order, Integer page, Integer size, String filter, String username) {
		Sort.Direction direction = !StringUtils.isEmpty(order) ? Sort.Direction.valueOf(order.toUpperCase()) : Sort.Direction.DESC;
		Pageable pg = PageRequest.of(page, size, Sort.by(new Sort.Order(direction, sort)));

		final UserEntity user = userService.getUserEntity(username);
		String role = userService.getRole(user);
		switch (role) {
			case ClubRole.ROLE_ADMIN: {
				//TODO Use Page and visible in query!!!
				return eventRepository.findByDescriptionContainingOrNameContainingOrCostTextContaining(filter, filter, filter, pg)
						.stream()
						.filter(AbstractEntity::getVisible)
						.map(event -> converterEventService.toEventDto(event))
						.collect(Collectors.toList());
			}
			case ClubRole.ROLE_CONCERT: {
				return eventRepository.findByNameContainingAndAccessEditContains(filter, Collections.singletonList(user), pg)
						.stream()
						.filter(AbstractEntity::getVisible).map(event -> converterEventService.toEventDto(event))
						.collect(Collectors.toList());
			}
		}
		return Collections.emptyList();
	}

	@Override
	public EventDTO getEvent(Long id) {
		return converterEventService.toEventDto(eventRepository.findById(id).orElse(new EventEntity()));
	}

	@Override
	public long getTotalEvents(String filter, String username) {
		return eventRepository.countByDescriptionContainingOrNameContainingOrCostTextContainingAndVisibleTrue(filter, filter, filter);
	}

	@Override
	public void delete(Long id) {
		eventRepository.findById(id).ifPresent(event -> {
			event.setVisible(Boolean.FALSE);
			eventRepository.save(event);
		});
	}

	@Override
	public Optional<EventEntity> getEventById(Long event) {
		return eventRepository.findById(event);
	}

	@Override
	public EventDTO save(EventDTO dto, String username) {
		UserEntity user = userService.getUserEntity(username);
		EventEntity entity;
		if (dto.getId() == null || dto.getId() < 1) {
			entity = new EventEntity();
			entity.getAccessEdit().add(user);
		} else {
			entity = eventRepository.findById(dto.getId()).orElseGet(EventEntity::new);
			if (!hasAccess(entity, user)) {
				throw new AccessControlException("This has't access of this event");
			}
		}
		entity = converterEventService.toEntity(dto, entity);
		entity.setVisible(TRUE);
		entity = eventRepository.save(entity);
		return converterEventService.toEventDto(entity);
	}

	public boolean hasAccess(EventEntity event, UserEntity user) {
		return event.getAccessEdit().contains(user) || user.getAuthorities().stream().map(AuthorityEntity::getAuthority).collect(Collectors.toList()).contains(ClubRole.ROLE_ADMIN);
	}
}
