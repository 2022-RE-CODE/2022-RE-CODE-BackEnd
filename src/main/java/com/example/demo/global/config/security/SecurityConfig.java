package com.example.demo.global.config.security;

import com.example.demo.global.error.CustomAuthenticationEntryPoint;
import com.example.demo.global.jwt.JwtProvider;
import com.example.demo.global.jwt.filter.JwtAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final JwtAuth jwtAuth;
    private final ObjectMapper objectMapper;
    private static final String ADMIN = "ADMIN";
    private static final String SUPER = "SUPER_ADMIN";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .cors()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                // user
                .antMatchers(HttpMethod.POST, "/user").permitAll()

                // admin
                .antMatchers(HttpMethod.POST, "/admin").permitAll()
                .antMatchers(HttpMethod.POST, "/admin/token").permitAll()
                .antMatchers(HttpMethod.PUT, "/admin").hasAnyRole(ADMIN, SUPER)

                // super admin
                .antMatchers(HttpMethod.GET, "/super").hasRole(SUPER)

                .anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper))

                .and()
                .apply(new FilterConfig(jwtProvider, jwtAuth, objectMapper));
        return http.build();
    }

}
