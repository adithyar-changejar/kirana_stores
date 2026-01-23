package com.example.kiranastore.config;

import com.example.kiranastore.auth.JwtAuthenticationFilter;
import com.example.kiranastore.auth.JwtTokenProvider;
import com.example.kiranastore.security.RateLimitFilter;
import com.example.kiranastore.service.RateLimitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final RateLimitService rateLimitService;

    public SecurityConfig(
            JwtTokenProvider jwtTokenProvider,
            RateLimitService rateLimitService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.rateLimitService = rateLimitService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/health").permitAll()
                        .requestMatchers("/stores/**").permitAll()
                        .requestMatchers("/cart/**").hasRole("USER")
                        .requestMatchers("/transactions/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterAfter(
                        new RateLimitFilter(rateLimitService),
                        JwtAuthenticationFilter.class
                );

        return http.build();
    }
}
