package com.springSecurity.api.BasicSecurity.config; // Fixed typo in package name

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for REST API (safe since using form login)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/register").permitAll() // Allow public access to register
                .requestMatchers(HttpMethod.GET,"/user").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/user/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT,"/update/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/delete/{id}").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated() // All other requests require authentication
            )
            // Form login is enabled; consider using HTTP Basic or JWT for pure REST API
            .httpBasic(httpBasic -> httpBasic
                .realmName("BasicSecurity")
            );
        return http.build();
    }
}