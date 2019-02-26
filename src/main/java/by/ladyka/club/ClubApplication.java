package by.ladyka.club;

import by.ladyka.club.config.Config;
import by.ladyka.club.config.DataSourceConfig;
import by.ladyka.club.service.ImageDownloaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ClubApplication {
	public static final String PACKAGES_TO_SCAN = ClubApplication.class.getPackage().getName();

	public static void main(String[] args) {
		SpringApplication.run(new Class[]{ClubApplication.class, DataSourceConfig.class, Config.class}, args);
	}

	@Autowired
	private ImageDownloaderService imageDownloaderService;

	private static final Logger logger = LoggerFactory.getLogger(ClubApplication.class);

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		logger.warn("START PROCESS IMAGES");
		imageDownloaderService.downloadImagesFromEvents();
	}
}
