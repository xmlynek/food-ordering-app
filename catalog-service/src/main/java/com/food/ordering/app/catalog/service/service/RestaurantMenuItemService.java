package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.entity.Restaurant;
import reactor.core.publisher.Mono;

public interface RestaurantMenuItemService {

  Mono<Restaurant> addMenuItem(String restaurantId, MenuItem menuItem);

  Mono<Restaurant> reviseMenuItem(String restaurantId, MenuItem menuItem);

  Mono<Restaurant> deleteMenuItem(String restaurantId, String menuItemId);
}
