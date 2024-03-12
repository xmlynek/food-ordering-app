package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RestaurantQueryService {

  Mono<Page<Restaurant>> findAllRestaurants(Pageable pageable);

  Mono<Restaurant> findRestaurantById(String id);

  Flux<MenuItem> findAllMenuItems(String restaurantId);

  Mono<MenuItem> findMenuItemById(String restaurantId, String menuItemId);

}
