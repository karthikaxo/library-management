package com.example.libraryapi;

import com.example.libraryapi.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private MyUserDetailsService myUserDetailsService;

    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

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

    // Auth provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(myUserDetailsService);

        // receive password, convert to bcrypt hash, then compare
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(7)); // match strength set in AccountServiceImpl
        return authProvider;
    }
}
