package com.example.parking.Security;

import com.example.parking.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * שרשרת אבטחה לסביבת פיתוח בלבד (dev).
     * מוגדרת בעדיפות גבוהה יותר (@Order(1)) כדי שתטפל ב-H2 Console לפני השרשרת הראשית.
     */
    @Configuration
    @Profile("dev")
    public static class DevSecurityConfig {

        @Bean
        @Order(1)
        public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                    .securityMatcher("/h2-console/**")
                    .csrf(csrf -> csrf.disable())
                    .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

            return http.build();
        }
    }

    /**
     * שרשרת האבטחה הראשית של האפליקציה.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // מאפשר תצוגת Frames גם ברמה הכללית
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login"
                        ).permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/transactions/exit/**").permitAll()
                        .requestMatchers("/api/transactions/start/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/users/getAllUsers").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers("/api/vehicles/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public org.springframework.security.authentication.AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}