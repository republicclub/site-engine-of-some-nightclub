package by.ladyka.club.entity.order;

import by.ladyka.bepaid.dto.GatewayStatus;
import by.ladyka.club.entity.AbstractEntity;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.entity.menu.MenuItemPricesHasOrders;
import lombok.Getter;
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
public class OrderEntity extends AbstractEntity {
	private String name;
	private String surname;
	private String email;
	private String phone;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventId")
	private EventEntity eventEntity;
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
	private int dance;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderEntity")
	private List<OrderItemEntity> tableNumbers = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	List<MenuItemPricesHasOrders> itemPricesHasOrders = new ArrayList<>();


//	private Integer people;
//	private Integer tableNumber;

	@Column(name = "enter_time")
	private LocalDateTime enterTime;

	@ManyToOne
	@JoinColumn(name = "acceptor_user_id")
	private UserEntity acceptor;

}
