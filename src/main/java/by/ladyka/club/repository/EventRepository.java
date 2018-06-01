package by.ladyka.club.repository;

import by.ladyka.club.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	List<Event> findAllByStartEventBetweenAndStatusGreaterThanEqual(LocalDateTime after, LocalDateTime before, int status);
}
