package com.htb_kg.ctf.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)
        http.cors().and().csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()//
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/job_seeker/**").permitAll()
                .antMatchers("/category/**").permitAll()
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/task/**").authenticated()
                .antMatchers("/hacker/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/vacancy/**").permitAll()
                .antMatchers("/api/v1/management/**").permitAll()
                .antMatchers("/level/**").permitAll()
                .antMatchers("/file/**").permitAll()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/wss/**").permitAll()
                .antMatchers("/main/**").permitAll()
        //.antMatchers("/chat/**").permitAll()
                // Disallow everything else..
                .anyRequest().authenticated();
        // Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

        http.formLogin().failureHandler(customAuthenticationFailureHandler());


        // Optional, if you want to test the API from a browser
        // http.httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v3/api-docs/**")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/swagger-ui/**")//
                .antMatchers("/api/v1/auth/**")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}