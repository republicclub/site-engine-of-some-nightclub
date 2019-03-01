package by.ladyka.club.config;

import by.ladyka.club.endpoints.api.HealthCheck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		final String endpointsPackage = HealthCheck.class.getName().substring(0, HealthCheck.class.getName().lastIndexOf("."));
		return new Docket(DocumentationType.SWAGGER_2)
				.directModelSubstitute(LocalDate.class, String.class)
				.directModelSubstitute(ZonedDateTime.class, String.class)
				.select()
				.apis(RequestHandlerSelectors.basePackage(endpointsPackage))
				.paths(PathSelectors.any())
				.build();
	}
}
