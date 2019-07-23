package by.ladyka.club.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorities")
@EntityListeners(AuditingEntityListener.class)
public class AuthorityEntity extends BasicEntity implements GrantedAuthority {

	private static final long serialVersionUID = 3L;
	private String authority;
	private String username;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
}
