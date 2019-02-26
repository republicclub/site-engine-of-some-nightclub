package by.ladyka.club.repository;

import by.ladyka.club.entity.order.OrderEntity;
import by.ladyka.club.entity.order.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
	Optional<OrderEntity> findByUuid(String uuid);

	List<OrderEntity> findAllByEventEntityIdAndTicketType(Long eventId, TicketType ticketType);
	List<OrderEntity> findTop5ByEventEntityIdAndUuidContains(Long eventId, String uuid);
}
