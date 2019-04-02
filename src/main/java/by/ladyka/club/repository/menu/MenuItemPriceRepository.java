package by.ladyka.club.repository.menu;

import by.ladyka.club.entity.menu.MenuItemPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemPriceRepository extends JpaRepository<MenuItemPrice, Long> {
	MenuItemPrice findByIdAndVisibleIsTrue(Long itemPriceId);
}
