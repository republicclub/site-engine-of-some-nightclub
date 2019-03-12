package by.ladyka.club.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.Table;



@Entity
@Getter
@Setter
@Table(name = "feedback")
@EntityListeners(AuditingEntityListener.class)
public class FeedBackEntity extends AbstractEntity {
	private String name;
	private String email;
	private String phone;
	@Lob
    private String description;
}
