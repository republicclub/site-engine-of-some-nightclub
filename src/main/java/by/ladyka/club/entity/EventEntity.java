package by.ladyka.club.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "club_event")
@EntityListeners(AuditingEntityListener.class)
public class EventEntity extends AbstractEntity {
	public static final String startEventFieldName = "startEvent";
	@Lob
	private String costText;
	@Lob
	private String description;
	private LocalDateTime startEvent;
	private LocalDateTime endEvent;
	private String name;
	private String coverUri;
	private Boolean recommendation;
	private Boolean republicPay;

	@OneToMany(mappedBy = "event")
	private List<ClubEventTicketPrice> ticketPrices = new ArrayList<>();
	@Deprecated
	private BigDecimal costDance;
	@Deprecated
	private BigDecimal costTablePlace;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "event_concert_access",
			joinColumns = {@JoinColumn(name = "event_id")},
			inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private List<UserEntity> accessEdit = new ArrayList<>();

	@Deprecated
	private String alias;

	@Deprecated
	private BigDecimal cost;

	@Deprecated
	private String buyTicketUrl;

	public EventEntity(Long eventId) {
		setId(eventId);
	}
}
