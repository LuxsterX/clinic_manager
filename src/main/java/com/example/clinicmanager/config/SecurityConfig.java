package com.example.clinicmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Wyłącz CSRF, jeśli używasz tokenów JWT (możesz to dostosować w zależności od wymagań)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/public/**").permitAll() // Endpoints dostępne publicznie
                        .anyRequest().authenticated() // Wymaga uwierzytelnienia dla innych endpointów
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Zezwalaj na pochodzenie tylko z frontendu
        config.addAllowedOriginPattern("http://localhost:3010"); // Zmień na frontendowy adres

        config.setAllowCredentials(true); // Zezwól na wysyłanie ciasteczek i nagłówków autoryzacji
        config.addAllowedHeader("*"); // Zezwalaj na wszystkie nagłówki
        config.addAllowedMethod("*"); // Zezwalaj na wszystkie metody HTTP (GET, POST, PUT, DELETE, itp.)

        // Zarejestruj powyższą konfigurację dla wszystkich endpointów
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}
