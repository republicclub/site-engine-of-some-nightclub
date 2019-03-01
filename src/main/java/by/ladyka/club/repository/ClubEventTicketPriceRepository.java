package by.ladyka.club.repository;

import by.ladyka.club.entity.ClubEventTicketPrice;
import by.ladyka.club.entity.EventTicketPriceType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubEventTicketPriceRepository extends JpaRepository<ClubEventTicketPrice, Long> {
    @Query("Select p from ClubEventTicketPrice p inner join p.event e where e.id = :eventId AND p.type = :eventTicketPriceType order by p.cost ASC")
    List<ClubEventTicketPrice> findAscSortPricesForEventByPriceType(@Param("eventId") Long eventId, @Param("eventTicketPriceType") EventTicketPriceType eventTicketPriceType, Pageable pageable);
}
