package com.uniSync.uniSync_api.config;

import org.springframework.context.annotation.Configuration;
import com.uniSync.uniSync_api.utils.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("Custom SecurityConfig loaded!");
        http
            .csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/", "/auth/**", "/test/**", "/actuator/health").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin().disable();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
} 