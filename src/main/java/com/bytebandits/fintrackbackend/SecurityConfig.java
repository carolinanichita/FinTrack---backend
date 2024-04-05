package com.bytebandits.fintrackbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(r -> r
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/user/register").permitAll()
                        .requestMatchers("/user/test").permitAll()
                        .requestMatchers("/user/logout").permitAll()
                        .requestMatchers("/transaction/save").permitAll()
                        .requestMatchers("/transaction").permitAll()
                        .requestMatchers("/transaction/{year}").permitAll()
                        .requestMatchers("/transaction/{year}/{month}").permitAll()
                        .requestMatchers("/transaction/calendar/{year}/{month}").permitAll()
                        .requestMatchers("/transaction/{year}/{month}/total").permitAll()
                        .requestMatchers("/categories/create").permitAll()
                        .requestMatchers("/accounts/create").permitAll()
                        .requestMatchers("/accounts").permitAll()
                        .requestMatchers("/accounts/{userId}").permitAll()
                        .requestMatchers("/categories/{userId}/{transactionType}").permitAll()
                        .requestMatchers("/categories/{userId}").permitAll()

                        .anyRequest().authenticated()
                )
                .csrf(c -> c.disable())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}


