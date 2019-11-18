package dz.wta.web.ooredoo.frontend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().disable();

		http.csrf().disable();// cockies..

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// STATELESS Pas session;

		http.authorizeRequests().antMatchers("/home", "/login", "/save-user", "/list-role", "/list-user", "/add", "/save", "/").permitAll()// Pas privilege .

				.antMatchers("/users", "/roles").hasAnyAuthority("ADMIN")

				.anyRequest().authenticated();

		http.addFilter(new JWTAuthentificationFilter(authenticationManager()));// Authentifier avec login si oui crier un token jwt
		http.addFilterBefore(new JWTAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class);// Authentifier avec token crié deja
	}

	@Bean
	BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}