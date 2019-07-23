package by.ladyka.club.repository;

import by.ladyka.club.entity.order.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, Long> {
	List<OrderItemEntity> findByTicketOrderEntityEventEntityId(Long eventId);
}
