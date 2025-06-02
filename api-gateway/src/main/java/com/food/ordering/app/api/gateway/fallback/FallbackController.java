package com.food.ordering.app.api.gateway.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FallbackController {

  @RequestMapping("/fallback/catalog-service")
  public Mono<String> catalogServiceFallback() {
    return Mono.just("Catalog service is currently unavailable. Please try again later.");
  }

  @RequestMapping("/fallback/order-service")
  public Mono<String> orderServiceFallback() {
    return Mono.just("Order service is currently unavailable. Please try again later.");
  }

  @RequestMapping("/fallback/restaurant-service")
  public Mono<String> restaurantServiceFallback() {
    return Mono.just("Restaurant service is currently unavailable. Please try again later.");
  }

  @RequestMapping("/fallback/kitchen-service")
  public Mono<String> kitchenServiceFallback() {
    return Mono.just("Kitchen service is currently unavailable. Please try again later.");
  }

  @RequestMapping("/fallback/delivery-service")
  public Mono<String> deliveryServiceFallback() {
    return Mono.just("Delivery service is currently unavailable. Please try again later.");
  }
}
