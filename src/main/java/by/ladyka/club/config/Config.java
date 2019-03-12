package by.ladyka.club.config;

import by.ladyka.bepaid.BePaidApi;
import by.ladyka.club.ClubApplication;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {
		"by.ladyka.club"
})
@EntityScan(
		basePackageClasses = {ClubApplication.class, Jsr310JpaConverters.class}
)
@AllArgsConstructor
public class Config {

	private final CustomSettings settings;


	@Bean
	public BePaidApi bePaidApi() {
		return BePaidApi.getApi(settings.getBePaidPaymentStoreId(), settings.getBePaidPaymentStoreKey(), (requestID, method, url, requestBody, code, resp) -> {
		});
	}


}
