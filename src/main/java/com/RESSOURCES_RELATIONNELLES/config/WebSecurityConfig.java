package com.RESSOURCES_RELATIONNELLES.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Autoriser toutes les requêtes
                .csrf(csrf -> csrf.disable()) // Désactiver CSRF pour éviter les erreurs sur les requêtes POST
                .formLogin(login -> login.disable()) // Désactiver la page de connexion
                .httpBasic(basic -> basic.disable()); // Désactiver l'authentification HTTP basique

        return http.build();
    }
}
