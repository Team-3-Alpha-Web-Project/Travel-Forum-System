package com.team_3.travel_forum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/api", "/error").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // TODO: replace with your actual endpoint paths once built,
                        .requestMatchers(HttpMethod.GET, "/api/posts/top-commented").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts/recent").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/username/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/search/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/*/block").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/*/unblock").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/*/promote").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("USER", "ADMIN")

                        // TODO: confirm against your future PostRestController / CommentRestController
                        .requestMatchers(HttpMethod.POST, "/api/posts/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/posts/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/comments/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/comments/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/comments/**").hasAnyRole("USER", "ADMIN")

                        // TODO: confirm actual likes endpoint path/controller
                        .requestMatchers(HttpMethod.POST, "/api/posts/*/likes").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
    }
}