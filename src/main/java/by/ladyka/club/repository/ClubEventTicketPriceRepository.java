package by.ladyka.club.repository;

import by.ladyka.club.entity.ClubEventTicketPrice;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.EventTicketPriceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ClubEventTicketPriceRepository extends JpaRepository<ClubEventTicketPrice, Long> {
	//TODO check count tickets
	Optional<ClubEventTicketPrice> findTop1ByStartActiveTimeIsBeforeAndEndActiveTimeAfterAndEventAndTypeOrderByCost(LocalDateTime startActiveTime, LocalDateTime endActiveTime, EventEntity event, EventTicketPriceType type);
}
