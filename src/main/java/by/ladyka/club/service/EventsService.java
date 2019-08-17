package by.ladyka.club.service;

import by.ladyka.club.dto.AppUser;
import by.ladyka.club.dto.EventDTO;
import by.ladyka.club.dto.EventRelevantDTO;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventsService {
	List<EventDTO> getEventsBetween(LocalDateTime after, LocalDateTime before);
	List<EventDTO> getEventsAfter(LocalDateTime time);
	List<EventDTO> getEventsAfterAndRepublicPayTrue(LocalDateTime time);
	List<EventRelevantDTO> getRelevantEvents(AppUser user);
	Optional<EventEntity> getEventById(Long event);
	EventDTO save(EventDTO event, String user);
	List<EventDTO> getEvents(String sort, String order, Integer page, Integer size, String filter, String username, boolean actual);
	EventDTO getEvent(Long id);
	long getTotalEvents(String filter, String username, boolean actual);
	void delete(Long id);
	boolean hasAccess(EventEntity event, UserEntity user);
}
