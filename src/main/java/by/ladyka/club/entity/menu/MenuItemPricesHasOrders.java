package by.ladyka.club.entity.menu;

import by.ladyka.club.entity.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_item_price_has_order")
@EntityListeners(AuditingEntityListener.class)
@Deprecated //MenuItemPricesHasTicketOrder
public class MenuItemPricesHasOrders extends BasicEntity {
	@Column(nullable = false)
	private int count;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	private MenuOrder order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "itemPriceId")
	private MenuItemPrice itemPrice;

}
