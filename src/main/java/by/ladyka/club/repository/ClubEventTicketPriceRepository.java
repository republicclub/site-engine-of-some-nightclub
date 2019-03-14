package by.ladyka.club.repository;

import by.ladyka.club.entity.ClubEventTicketPrice;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.EventTicketPriceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClubEventTicketPriceRepository extends JpaRepository<ClubEventTicketPrice, Long> {

    @Query("SELECT p " +
            "FROM ClubEventTicketPrice p " +
            "LEFT JOIN p.danceOrders o " +
            "WHERE :dateTime BETWEEN p.startActiveTime AND p.endActiveTime " +
            "AND p.type = 'dance' " +
            "AND p.event.id = :eventId " +
            "AND o.ticketType = 0 " +
            "GROUP BY p.id " +
            "HAVING p.quantity > SUM(o.dance) OR SUM(o.dance) IS NULL " +
            "ORDER BY p.cost ASC")
    List<ClubEventTicketPrice> findActiveAndLowestAndAvailableDancePriceForEvent(@Param("dateTime") LocalDateTime dateTime, @Param("eventId") Long eventId);




    @Query(value = "SELECT p " +
            "FROM ClubEventTicketPrice p " +
            "LEFT JOIN p.tableOrders o " +
            "LEFT JOIN o.tableNumbers i " +
            "WHERE :dateTime BETWEEN p.startActiveTime AND p.endActiveTime " +
            "AND p.type = 'table' " +
            "AND p.event.id = :eventId " +
            "AND o.ticketType = 0 " +
            "GROUP BY p.id " +
            "HAVING p.quantity > COUNT(i.id) OR COUNT(i.id) IS NULL " +
            "ORDER BY p.cost ASC")
    List<ClubEventTicketPrice> findActiveAndLowestAndAvailableTablePriceForEvent(@Param("dateTime") LocalDateTime dateTime, @Param("eventId") Long eventId);

}
