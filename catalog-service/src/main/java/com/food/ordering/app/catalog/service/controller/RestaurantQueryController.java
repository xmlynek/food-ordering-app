package com.food.ordering.app.catalog.service.controller;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.catalog.service.service.RestaurantQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/catalog/restaurants")
public class RestaurantQueryController {

  private final RestaurantQueryService restaurantQueryService;

  @Autowired
  public RestaurantQueryController(RestaurantQueryService restaurantQueryService) {
    this.restaurantQueryService = restaurantQueryService;
  }

//  @GetMapping
//  public Mono<Page<Restaurant>> getAllRestaurants(
//      @RequestParam(value = "page", defaultValue = "0") int page,
//      @RequestParam(value = "size", defaultValue = "10") int size) {
//    return restaurantQueryService.findAllRestaurants(page, size);
//  }

  @GetMapping
  public Mono<Page<Restaurant>> getAllRestaurants(
     @PageableDefault(size = 20) final Pageable pageable) {
    return restaurantQueryService.findAllRestaurants(pageable);
  }

  @GetMapping("/{id}")
  public Mono<Restaurant> getRestaurantById(@PathVariable String id) {
    return restaurantQueryService.findRestaurantById(id);
  }

  @GetMapping("/{restaurantId}/menuItems")
  public Flux<MenuItem> getRestaurantMenuItems(@PathVariable String restaurantId) {
    return restaurantQueryService.findAllMenuItems(restaurantId);
  }

  @GetMapping("/{restaurantId}/menuItems/{menuItemId}")
  public Mono<MenuItem> getRestaurantMenuItemById(@PathVariable String restaurantId,
      @PathVariable String menuItemId) {
    return restaurantQueryService.findMenuItemById(restaurantId, menuItemId);
  }
}
