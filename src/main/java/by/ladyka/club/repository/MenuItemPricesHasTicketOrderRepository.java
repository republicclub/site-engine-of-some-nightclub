package by.ladyka.club.repository;

import by.ladyka.club.entity.menu.MenuItemPricesHasTicketOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemPricesHasTicketOrderRepository extends JpaRepository<MenuItemPricesHasTicketOrder, Long> {
}
