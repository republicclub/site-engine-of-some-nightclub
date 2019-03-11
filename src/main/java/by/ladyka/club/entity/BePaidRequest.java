package by.ladyka.club.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "be_paid_request")
@EntityListeners(AuditingEntityListener.class)
public class BePaidRequest extends AbstractEntity {
	private String requestId;
	private String method;
	private String url;
	@Lob
	private String requestBody;
	private int code;
	@Lob
	private String responseBody;
}
