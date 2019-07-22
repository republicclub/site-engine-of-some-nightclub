package by.ladyka.club.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_data_statistic")
@EntityListeners(AuditingEntityListener.class)
public class UserDataStatisticEntity  extends BasicEntity {

	String appCodeName;
	String appName;
	String appVersion;
	boolean cookieEnabled;
	String language;
	boolean onLine;
	String platform;
	int seconds;
	String userAgent;
	String localAddr;
	String remoteAddr;
	String protocol;

}
