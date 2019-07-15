package by.ladyka.club.repository;

import by.ladyka.club.entity.ClubEventTicketPrice;
import by.ladyka.club.entity.order.TicketOrderEntity;
import by.ladyka.club.entity.order.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderEntityRepository extends JpaRepository<TicketOrderEntity, Long> {
	Optional<TicketOrderEntity> findByUuid(String uuid);

	List<TicketOrderEntity> findAllByEventEntityIdAndTicketType(Long eventId, TicketType ticketType);
	List<TicketOrderEntity> findTop5ByEventEntityIdAndUuidContains(Long eventId, String uuid);

	long countByClubEventTicketPrice(ClubEventTicketPrice clubEventTicketPrice);
}
