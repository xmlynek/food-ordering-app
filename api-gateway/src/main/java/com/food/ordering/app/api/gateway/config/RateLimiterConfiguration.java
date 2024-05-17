package com.food.ordering.app.api.gateway.config;

import java.net.InetSocketAddress;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfiguration {

  @Primary
  @Bean
  public KeyResolver ipKeyResolver() {
    return exchange -> {
      XForwardedRemoteAddressResolver resolver = XForwardedRemoteAddressResolver.maxTrustedIndex(1);
      InetSocketAddress inetSocketAddress = resolver.resolve(exchange);
      return Mono.just(inetSocketAddress.getAddress().getHostAddress());
    };
  }
}
