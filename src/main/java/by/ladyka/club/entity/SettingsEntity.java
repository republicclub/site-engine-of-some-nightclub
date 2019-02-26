package by.ladyka.club.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "site_settings")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class SettingsEntity extends AbstractEntity {
	@Column(name = "settings_key")
	private String key;
	@Column(name = "settings_value")
	private String value;
}
