package by.ladyka.club.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "club_event_ticket_price")
@EntityListeners(AuditingEntityListener.class)
public class ClubEventTicketPrice extends AbstractEntity {
    private BigDecimal cost;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private EventTicketPriceType type;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;
    @Column(name = "start_active_time")
    private LocalDateTime startActiveTime;
    @Column(name = "end_active_time")
    private LocalDateTime endActiveTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity modifiedBy;
}
