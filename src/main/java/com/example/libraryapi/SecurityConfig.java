package com.example.libraryapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // disable csrf since postman / frontend don't need CSRF protection
                .csrf(customizer -> customizer.disable())
                // apply authentication for endpoints
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                // postman, REST APIs (basic auth)
                .httpBasic(Customizer.withDefaults())
                // stateless session since auth is done through JWT
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .build();
    }
}
