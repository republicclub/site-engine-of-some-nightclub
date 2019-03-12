package by.ladyka.club.entity.menu;

import by.ladyka.bepaid.dto.GatewayStatus;
import by.ladyka.club.entity.AbstractEntity;
import by.ladyka.club.entity.EventEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Entity
@Getter
@Setter
@Table(name = "menu_order")
@EntityListeners(AuditingEntityListener.class)
@ToString
@Deprecated
public class MenuOrder extends AbstractEntity {
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
	List<MenuItemPricesHasOrders> itemPricesHasOrders = new ArrayList<>();
	private String name;
	private String surname;
	private String email;
	private String phone;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventId")
	private EventEntity eventEntity;
	private String arrivalTime;
	private Integer people;
	@Lob
	private String description;
	private Integer tableNumber;

	@Column(name = "uuid", unique = true, nullable = false)
	private String uuid = UUID.randomUUID().toString();
	private String token;

	@Enumerated(EnumType.STRING)
	private GatewayStatus payStatus;
	private String uid;
}
