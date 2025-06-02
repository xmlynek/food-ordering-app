package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface RestaurantMenuItemQueryService {

  Mono<Page<MenuItem>> findAllAvailableMenuItems(String restaurantId, Pageable pageable);

  Mono<MenuItem> findMenuItemById(String restaurantId, String menuItemId);
}
