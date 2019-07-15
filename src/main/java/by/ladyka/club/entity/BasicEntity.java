package by.ladyka.club.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BasicEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;

	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "last_modified_date")
	private LocalDateTime lastModifiedDate;

	private Boolean visible = Boolean.TRUE;
}
