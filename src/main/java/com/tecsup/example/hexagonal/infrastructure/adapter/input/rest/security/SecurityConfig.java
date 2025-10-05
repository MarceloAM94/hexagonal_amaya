package com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desactiva CSRF para simplificar pruebas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/dni/**", "/api/users/apellido/**").hasRole("ADMIN")
                        .requestMatchers("/api/users/menores/**").hasRole("MONITOR")
                        .requestMatchers(HttpMethod.POST, "/api/users").authenticated()
                        .anyRequest().permitAll()
                )
                .httpBasic(withDefaults()); // usa autenticación básica

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin123") // {noop} = sin codificar (solo para desarrollo)
                .roles("ADMIN")
                .build();

        UserDetails monitor = User.builder()
                .username("monitor")
                .password("{noop}monitor123")
                .roles("MONITOR")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password("{noop}user123")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, monitor, user);
    }
}
