package by.ladyka.club;

import by.ladyka.club.config.Config;
import by.ladyka.club.config.DataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class[]{ClubApplication.class, DataSourceConfig.class, Config.class}, args);
	}
}
