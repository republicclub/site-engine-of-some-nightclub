package by.ladyka.club.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends BasicEntity implements UserDetails {

	@Getter
	@Setter
	private String username;

	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	@Type(type = "boolean")
	private Boolean enabled;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String surname;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String phone;

	@Getter
	@Setter
	@Column(name = "father_name")
	private String fatherName;

	@Getter
	@Setter
	private OffsetDateTime birthday;

	@Setter
	@Type(type = "boolean")
	private Boolean accountNonExpired;

	@Setter
	@Type(type = "boolean")
	private Boolean accountNonLocked;
	@Setter
	@Type(type = "boolean")
	private Boolean credentialsNonExpired;

	@Getter
	@Setter
	@OneToMany(mappedBy = "user")
	private List<AuthorityEntity> authorities = new ArrayList<>();
	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			},
			mappedBy = "accessEdit")
	private List<EventEntity> accessEdit = new ArrayList<>();

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public String getPublishName() {
		return String.format("%s %s", getSurname(), getName());
	}
}
