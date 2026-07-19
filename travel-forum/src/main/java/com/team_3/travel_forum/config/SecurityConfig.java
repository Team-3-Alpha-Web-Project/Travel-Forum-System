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
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        /*
                         * Public application routes
                         */
                        .requestMatchers(
                                "/",
                                "/home",
                                "/api",
                                "/error"
                        ).permitAll()

                        /*
                         * Public home-page data
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/stats",
                                "/api/posts/recent",
                                "/api/posts/top-commented",
                                "/api/posts/*/likes/count"
                        ).permitAll()

                        /*
                         * Registration
                         */
                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        /*
                         * Swagger
                         */
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        /*
                         * Static resources
                         */
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        /*
                         * Current user's own profile.
                         *
                         * These must come before the broader
                         * /api/users/* administrator matcher.
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/users/me"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/users/me"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/users/me/password"
                        ).authenticated()

                        /*
                         * Administrator user management
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/users",
                                "/api/users/*",
                                "/api/users/username/**",
                                "/api/users/search/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/users/*"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/users/*/block",
                                "/api/users/*/unblock",
                                "/api/users/*/promote"
                        ).hasRole("ADMIN")

                        /*
                         * Authenticated post browsing
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/posts",
                                "/api/posts/*",
                                "/api/posts/search",
                                "/api/users/*/posts"
                        ).authenticated()

                        /*
                         * Authenticated comment browsing
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/posts/*/comments",
                                "/api/comments/*",
                                "/api/users/*/comments"
                        ).authenticated()

                        /*
                         * Creating and modifying posts
                         */
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/posts"
                        ).hasAnyRole("USER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/posts/*"
                        ).hasAnyRole("USER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/posts/*"
                        ).hasAnyRole("USER", "ADMIN")

                        /*
                         * Creating and modifying comments
                         */
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/posts/*/comments"
                        ).hasAnyRole("USER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/comments/*"
                        ).hasAnyRole("USER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/comments/*"
                        ).hasAnyRole("USER", "ADMIN")

                        /*
                         * Likes
                         */
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/posts/*/likes"
                        ).hasAnyRole("USER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/posts/*/likes"
                        ).hasAnyRole("USER", "ADMIN")

                        /*
                         * Everything else requires authentication.
                         */
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults())

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
    }
}