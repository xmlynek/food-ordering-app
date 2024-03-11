package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.common.event.RestaurantRevisedEvent;
import reactor.core.publisher.Mono;

public interface RestaurantService {

  Mono<Restaurant> createRestaurant(Restaurant restaurant);

  Mono<Restaurant> reviseRestaurant(String id, RestaurantRevisedEvent restaurantRevisedEvent);

  Mono<Void> deleteRestaurant(String id);

}
