package com.htb_kg.ctf.security;

import com.amazonaws.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(  "/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/event/**",
                        "/task/**",
                        "/hacker/**",
                        "/api/v1/management/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/event/**").authenticated()
                .requestMatchers("/category/**").permitAll()
                .requestMatchers("/admin/**").permitAll()
                .requestMatchers("/task/**").permitAll()
                .requestMatchers("/hacker/**").permitAll()
                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/vacancy/**").permitAll()
                .requestMatchers("/api/v1/management/**").permitAll()
                .requestMatchers("/level/**").permitAll()
                .requestMatchers("/file/**").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/ws/**").permitAll()
                .requestMatchers("/wss/**").permitAll()
                .requestMatchers("/main/**").permitAll()
                .anyRequest().authenticated());
        return http.build();
    }
}


