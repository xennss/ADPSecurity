package com.example.ADPProject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/account/token", "/account/register", "/api/customers", "/api/customers/byname/").permitAll() // Permitir acceso sin autenticación
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
            );
        return http.build();
    }
}
