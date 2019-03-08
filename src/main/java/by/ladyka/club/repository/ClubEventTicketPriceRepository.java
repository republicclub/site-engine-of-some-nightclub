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
    @Query("Select p from ClubEventTicketPrice p inner join p.event e where e.id = :eventId AND p.type = :eventTicketPriceType order by p.cost ASC")
    List<ClubEventTicketPrice> findAscSortPricesForEventByPriceType(@Param("eventId") Long eventId, @Param("eventTicketPriceType") EventTicketPriceType eventTicketPriceType, Pageable pageable);

    @Query("SELECT p " +
            "FROM ClubEventTicketPrice p " +
            "LEFT JOIN p.danceOrders o " +
            "WHERE :now BETWEEN p.startActiveTime AND p.endActiveTime " +
            "AND p.type = 'dance' " +
            "AND p.event.id = :eventId " +
            "AND o.ticketType = 0 " +
            "GROUP BY p.id " +
            "HAVING p.quantity > SUM(o.dance) OR SUM(o.dance) IS NULL " +
            "ORDER BY p.cost ASC")
    List<ClubEventTicketPrice> findActiveAndLowestAndAvailableDancePriceForEvent(@Param("now") LocalDateTime now, @Param("eventId") Long eventId);




    @Query(value = "SELECT p " +
            "FROM ClubEventTicketPrice p " +
            "LEFT JOIN p.tableOrders o " +
            "LEFT JOIN o.tableNumbers i " +
            "WHERE :now BETWEEN p.startActiveTime AND p.endActiveTime " +
            "AND p.type = 'table' " +
            "AND p.event.id = :eventId " +
            "AND o.ticketType = 0 " +
            "GROUP BY p.id " +
            "HAVING p.quantity > COUNT(i.id) OR COUNT(i.id) IS NULL " +
            "ORDER BY p.cost ASC")
    List<ClubEventTicketPrice> findActiveAndLowestAndAvailableTablePriceForEvent(@Param("now") LocalDateTime now, @Param("eventId") Long eventId);

}
