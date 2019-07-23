package by.ladyka.club.entity.menu;

import by.ladyka.club.entity.BasicEntity;
import by.ladyka.club.entity.order.TicketOrderEntity;
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
@Table(name = "menu_item_price_has_ticket_order")
@EntityListeners(AuditingEntityListener.class)
public class MenuItemPricesHasTicketOrder extends BasicEntity {

	@Column(nullable = false)
	private int count;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private TicketOrderEntity order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_price_id")
	private MenuItemPrice itemPrice;
}
