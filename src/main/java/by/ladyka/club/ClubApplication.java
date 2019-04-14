package by.ladyka.club;

import by.ladyka.club.config.Config;
import by.ladyka.club.config.DataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"by.ladyka.club", "by.ladyka.merchshop"})
@EnableJpaRepositories({"by.ladyka.club", "by.ladyka.merchshop"})
@EntityScan( basePackages = {"by.ladyka.club", "by.ladyka.merchshop"} )
@EnableScheduling
public class ClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class[]{ClubApplication.class, DataSourceConfig.class, Config.class}, args);
	}
}
