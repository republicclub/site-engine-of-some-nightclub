package by.ladyka.club.entity.order;

import by.ladyka.club.entity.BasicEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ticket_order_item")
@EntityListeners(AuditingEntityListener.class)
@ToString
public class OrderItemEntity extends BasicEntity {

	@ManyToOne
	@JoinColumn(name = "order_id")
	private TicketOrderEntity ticketOrderEntity;
	private int tableNumber;
	private int place;

}
