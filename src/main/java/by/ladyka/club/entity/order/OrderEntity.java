package by.ladyka.club.entity.order;

import by.ladyka.bepaid.dto.GatewayStatus;
import by.ladyka.club.entity.AbstractEntity;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.entity.menu.MenuItemPricesHasOrders;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "ticket_order")
@EntityListeners(AuditingEntityListener.class)
@ToString
@NoArgsConstructor
public class OrderEntity extends AbstractEntity {
	private String name;
	private String surname;
	private String email;
	private String phone;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventId")
	private EventEntity eventEntity;
	@Lob
	private String description;

	@Column(name = "uuid", unique = true, nullable = false)
	private String uuid = UUID.randomUUID().toString();
	private String token;

	@Enumerated(EnumType.STRING)
	private GatewayStatus payStatus;
	private String uid;
	@Column(name = "order_sum")
	private BigDecimal totalOrder;



	//Paid Values
	private Long dance;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderEntity")
	private List<OrderItemEntity> tableNumbers = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	List<MenuItemPricesHasOrders> itemPricesHasOrders = new ArrayList<>();

	@Column(name = "enter_time")
	private LocalDateTime enterTime;

	@ManyToOne
	@JoinColumn(name = "acceptor_user_id")
	private UserEntity acceptor;

	@ManyToOne
	@JoinColumn(name = "book_user_id")
	private UserEntity bookUser;

	@Column(name = "ticket_type")
	private TicketType ticketType;

	public OrderEntity(OrderEntity entity) {
		this.name = entity.getName();
		this.surname = entity.getSurname();
		this.email = entity.getEmail();
		this.phone = entity.getPhone();
		this.eventEntity = entity.getEventEntity();
		this.description = entity.getDescription();
		this.uuid = entity.getUuid();
		this.token = entity.getToken();
		this.payStatus = entity.getPayStatus();
		this.uid = entity.getUid();
		this.totalOrder = entity.getTotalOrder();
		this.dance = entity.getDance();
		this.tableNumbers = entity.getTableNumbers();
		this.itemPricesHasOrders = entity.getItemPricesHasOrders();
		this.enterTime = entity.getEnterTime();
		this.acceptor = entity.getAcceptor();
		this.bookUser = entity.getBookUser();
		this.ticketType = entity.getTicketType();
	}

	public void setNewUuid() {
		setUuid(UUID.randomUUID().toString());
	}
}
