package by.ladyka.club.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/admin/**").hasAnyAuthority(ClubRole.ROLE_ADMIN, ClubRole.ROLE_CONCERT)
				.antMatchers("/api/tickets/report/event").hasAnyAuthority(ClubRole.ROLE_ADMIN, ClubRole.ROLE_CONCERT)
				.antMatchers("/api/tickets/accept").hasAnyRole(ClubRole.ROLE_ADMIN, ClubRole.ROLE_CONCERT)
				.antMatchers("/api/private/**").authenticated()
				.antMatchers("/", "/**").permitAll()
				.anyRequest().fullyAuthenticated()
				.and()
				.formLogin()
				.loginProcessingUrl("/api/loginprocessing")
				.failureForwardUrl("/api/login/fail")
				.successForwardUrl("/api/login/success")
				.permitAll()
				.and()
				.logout()
				.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return encodedPassword.contentEquals(rawPassword);
			}
		});
	}
}
