package com.example.libraryapi;

import com.example.libraryapi.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // disable csrf since postman / frontend don't need CSRF protection
                .csrf(customizer -> customizer.disable())

                // apply authentication for endpoints
                .authorizeHttpRequests(request -> request

                        // public
                        .requestMatchers(
                                "/account/create-mem",
                                "/account/login")
                        .permitAll()

                        // other endpoints authenticated
                        .anyRequest().authenticated())

                // postman, REST APIs (basic auth)
                .httpBasic(Customizer.withDefaults())

                // stateless session since auth is done through JWT
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    // credentials check
    @Bean
    public AuthenticationProvider authenticationProvider() {

        // loadByUsername(String username) : find user
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(myUserDetailsService);

        // receive password, BCrypt compare with stored hash, then true/false
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(7)); // strength set in AccountServiceImpl
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {

        return config.getAuthenticationManager(); // auth manager talks to auth provider
    }
}
