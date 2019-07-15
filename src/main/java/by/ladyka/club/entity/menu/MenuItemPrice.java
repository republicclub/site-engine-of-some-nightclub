package by.ladyka.club.entity.menu;

import by.ladyka.club.entity.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_item_price")
@EntityListeners(AuditingEntityListener.class)
public class MenuItemPrice extends BasicEntity {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private BigDecimal value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "itemId")
	private MenuItem item;
}
