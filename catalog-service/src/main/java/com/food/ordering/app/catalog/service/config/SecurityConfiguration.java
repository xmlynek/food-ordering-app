package com.food.ordering.app.catalog.service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

  @Bean
  public ReactiveJwtDecoder jwtDecoder() {
    return NimbusReactiveJwtDecoder.withJwkSetUri(
        oAuth2ResourceServerProperties.getJwt().getJwkSetUri()).build();
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.authorizeExchange(
            exchanges -> exchanges.pathMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/actuator/**",
                    "/actuator",
                    "/csrf",
                    "/v3/api-docs",
                    "/v3/api-docs/swagger-config",
                    "/swagger-resources/**")
                .permitAll()
                .pathMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .anyExchange().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder())));

    http.csrf(CsrfSpec::disable);

    return http.build();
  }
}