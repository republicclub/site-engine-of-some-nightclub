package by.ladyka.club.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;



@Entity
@Getter
@Setter
@Table(name = "event_report_image")
@EntityListeners(AuditingEntityListener.class)
public class EventReportImageEntity extends BasicEntity {
	@ManyToOne
	@JoinColumn(name = "event_report")
	private EventReportEntity eventReport;
	private String imageUrl;
	private String size;
	private Long width;
	private Long height;
}

