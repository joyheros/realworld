package top.ruilink.realworld.auth.security;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.domain.support.UserProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Autowired
	private UserProvider userRepository;
	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable).cors((cors) -> cors.configurationSource(corsConfigurationSource()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/users", "/api/users/login").permitAll()
						.requestMatchers("/api/articles", "/api/articles/{slug}", "/api/articles/{slug}/**",
								"/api/profiles/{username}", "/api/profiles/{username}/**", "/api/tags", "/api/user")
						.permitAll().anyRequest().authenticated())
				.authenticationProvider(authenticationProvider());
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return username -> {
			Optional<User> result = userRepository.findByEmail(username);
			if (result.isEmpty()) {
				throw new UsernameNotFoundException("User not found.");
			}
			User user = result.get();
			UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
					.password(user.getPassword()).authorities(Collections.emptyList()).accountExpired(false)
					.accountLocked(false).credentialsExpired(false).disabled(false).build();
			return userDetails;
		};
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration cors = new CorsConfiguration();
		// cors.setAllowedOriginPatterns(Arrays.asList("http://localhost"));
		cors.setAllowedOriginPatterns(List.of("*"));
		cors.setAllowedMethods(List.of("*"));
		cors.setAllowedHeaders(List.of("*"));
		cors.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", cors);
		return source;
	}
}
