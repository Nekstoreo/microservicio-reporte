package com.onclass.reporte.infrastructure.configuration;

import com.onclass.reporte.infrastructure.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApplicationProperties appProperties;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .pathMatchers(HttpMethod.POST, ApiConstants.REPORTS_BOOTCAMPS_PATH).authenticated()
                        .pathMatchers(HttpMethod.POST, ApiConstants.REPORTS_BOOTCAMPS_PATH + "/*/enrollments").authenticated()
                        .pathMatchers(HttpMethod.GET, ApiConstants.REPORTS_BOOTCAMPS_PATH + "/most-enrolled").authenticated()
                        .pathMatchers(HttpMethod.PATCH, ApiConstants.REPORTS_BOOTCAMPS_PATH + "/**").authenticated()
                        .anyExchange().permitAll())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}