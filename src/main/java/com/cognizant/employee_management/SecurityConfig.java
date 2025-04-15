package com.cognizant.employee_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

@EnableMethodSecurity

public class SecurityConfig {
 
    @Autowired

    private JwtRequestFilter jwtAuthFilter;
 
    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)//cross-site-requeust-forgery

            .sessionManagement(session -> session.sessionCreationPolicy
            		(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/v3/api-docs/**",    // Allow access to OpenAPI docs
                        "/swagger-ui/**",     // Allow access to Swagger UI
                        "/swagger-ui.html" ,
                        "/api/auth/**","/api/leave/**","/api/leavebalance/**","/api/**").permitAll()

                .requestMatchers("/api/manager/**").hasRole("MANAGER")

                .requestMatchers("/api/employee/**").hasAnyRole("EMPLOYEE", "MANAGER")

                .anyRequest().authenticated()

            )

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
 
        return http.build();

    }
 
    @Bean

	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}
    @Bean

	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		return config.getAuthenticationManager();

	}


}

 