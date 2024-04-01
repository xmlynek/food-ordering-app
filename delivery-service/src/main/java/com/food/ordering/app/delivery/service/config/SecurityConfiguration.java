package com.food.ordering.app.delivery.service.config;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  public static final String JWT_ROLES_CLAIM_NAME = "roles";
  public static final String GRANTED_AUTHORITY_ROLE_PREFIX = "ROLE_";

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry.requestMatchers(
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/actuator/**",
                "/actuator",
                "/csrf",
                "/v3/api-docs",
                "/v3/api-docs/swagger-config",
                "/swagger-resources/**")
            .permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated()
    );

    http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS));

    http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
        jwt.jwtAuthenticationConverter(this::jwtAuthenticationConverter)));

    return http.build();
  }


  private JwtAuthenticationToken jwtAuthenticationConverter(Jwt jwt) {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    List<GrantedAuthority> authorities = Stream.concat(
        jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
        Optional.ofNullable((List<String>) jwt.getClaims().get(JWT_ROLES_CLAIM_NAME))
            .orElse(Collections.emptyList())
            .stream()
            .map(role -> new SimpleGrantedAuthority(GRANTED_AUTHORITY_ROLE_PREFIX + role))
    ).collect(Collectors.toList());

    return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
  }
}
