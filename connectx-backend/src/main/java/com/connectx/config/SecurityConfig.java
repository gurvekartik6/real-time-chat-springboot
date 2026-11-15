package com.connectx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // REST API security configuration
                .csrf(csrf -> csrf.disable())

                // Define public and protected endpoints
                .authorizeHttpRequests(auth -> auth

                        // Public health endpoint
                        .requestMatchers("/api/v1/health").permitAll()

                        // Registration must be publicly accessible
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/users"
                        ).permitAll()

                        // All remaining endpoints require authentication
                        .anyRequest().authenticated()
                )

                // JWT APIs will not use server-side HTTP sessions
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // We will implement token-based authentication next
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }
}