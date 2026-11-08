package com.project.movie.config;

import com.project.movie.security.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class ProjectSecurityConfigurations {

	
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	public ProjectSecurityConfigurations(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
			http
				.csrf(csrf -> csrf.disable()) // Disable CSRF for development
				.cors(withDefaults()) // Enable CORS
				.authorizeHttpRequests(authorizeRequests -> 
						authorizeRequests
						.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN") // Restrict access to /admin/** to ADMIN role
						.requestMatchers("/user/**").hasRole("USER") // Restrict access to /user/** to USER role
						.anyRequest().permitAll()) // All other requests require authentication
				
                .formLogin(formLogin ->
                	formLogin
                        .successHandler(customAuthenticationSuccessHandler) // Use custom success handler for redirection
                        .permitAll()) // Allow all requests to /login			
                .logout(logout -> logout.permitAll()); // Allow all requests to /logout
		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000") // Allow requests from your frontend
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Allow these HTTP methods
//						.allowedHeaders("*").allowCredentials(true); // Allow cookies and credentials
			}
		};
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
				.build();

		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("user123")).roles("USER")
				.build();

		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
