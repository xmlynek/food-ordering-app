package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import reactor.core.publisher.Mono;

public interface RestaurantMenuItemService {

  Mono<MenuItem> addMenuItem(String restaurantId, MenuItem menuItem);

  Mono<MenuItem> reviseMenuItem(MenuItem menuItem);

  Mono<Void> deleteMenuItem(String menuItemId);
}
