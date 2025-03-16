package com.food.ordering.app.api.gateway.config;

import com.food.ordering.app.api.gateway.handler.SimilarMenuItemsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class ApiRouterConfiguration {

  @Bean
  public RouterFunction<ServerResponse> similarMenuItemsRoute(SimilarMenuItemsHandler handler) {
    return RouterFunctions.route()
        .POST("/api/v1/similar-menu-items", handler::findSimilarMenuItems)
        .build();
  }
}
