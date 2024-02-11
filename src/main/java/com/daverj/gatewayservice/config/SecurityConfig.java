package com.daverj.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeExchange((exchange -> exchange
                        .pathMatchers("/login/**").permitAll()
                        .pathMatchers("/eureka").permitAll()
                        .pathMatchers(HttpMethod.POST,"/account/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/account/**").hasAnyRole("CUSTOMER", "ADMIN")
                        .pathMatchers("/account/profile/**").hasAnyRole("CUSTOMER", "ADMIN")
                        .pathMatchers("/media/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/media/timeline/**").hasAnyRole("CUSTOMER", "ADMIN")
                        .pathMatchers(HttpMethod.GET, "/test/**").permitAll()
                        .anyExchange()
                        .authenticated()))

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> {
                            jwtConfigurer.jwtAuthenticationConverter(new JWTConverter());
                        })
                )
                .cors(cors -> cors.disable());

        return http.build();
    }

}
