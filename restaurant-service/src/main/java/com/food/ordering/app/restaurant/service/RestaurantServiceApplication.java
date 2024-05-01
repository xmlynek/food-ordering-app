package com.food.ordering.app.restaurant.service;

import com.food.ordering.app.common.config.JsonObjectMapperJavaTimeModuleConfiguration;
import com.food.ordering.app.restaurant.service.config.AppGCPConfigProperties;
import io.eventuate.tram.spring.flyway.EventuateTramFlywayMigrationConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.OncePerRequestFilter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({AppGCPConfigProperties.class})
@Import({
    OptimisticLockingDecoratorConfiguration.class,
    JsonObjectMapperJavaTimeModuleConfiguration.class,
    EventuateTramFlywayMigrationConfiguration.class
})
public class RestaurantServiceApplication {

  private final Logger _logger = LoggerFactory.getLogger(getClass());


  @Bean
  public OncePerRequestFilter logFilter() {
    return new OncePerRequestFilter() {
      @Override
      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
          FilterChain filterChain) throws ServletException, IOException {
        _logger.info("Path: {}", request.getRequestURI());
        filterChain.doFilter(request, response);
        _logger.info("Path: {} {}", request.getRequestURI(), response.getStatus());
      }
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(RestaurantServiceApplication.class, args);
  }

}
