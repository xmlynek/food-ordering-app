package com.food.ordering.app.order.service.service;

import com.food.ordering.app.common.event.RestaurantRevisedEvent;
import com.food.ordering.app.order.service.entity.Restaurant;
import java.util.UUID;

public interface RestaurantService {

  Restaurant createRestaurant(Restaurant restaurant);

  Restaurant reviseRestaurant(UUID id, RestaurantRevisedEvent restaurantRevisedEvent);

  void deleteRestaurant(UUID id);

}
