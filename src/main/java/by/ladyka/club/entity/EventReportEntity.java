package by.ladyka.club.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@Table(name = "event_report")
@EntityListeners(AuditingEntityListener.class)
public class EventReportEntity extends BasicEntity {
	private LocalDateTime startEvent;
	private String name;
	private String coverUri;

	@OneToMany(mappedBy = "eventReport")
	private List<EventReportImageEntity> images = new ArrayList<>();
}
