package by.ladyka.club.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;




@Entity
@Getter
@Setter
@Table(name = "news")
@EntityListeners(AuditingEntityListener.class)
public class NewsEntity extends BasicEntity {

	private String title;
	@Lob
	private String descriptionPreview;
	@Lob
	private String description;
	private String alias;
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ownerId")
	private UserEntity owner;
}
