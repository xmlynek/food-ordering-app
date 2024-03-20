package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.restaurant.service.dto.RestaurantUpdateRequest;
import com.food.ordering.app.restaurant.service.entity.Restaurant;
import java.util.List;
import java.util.UUID;

public interface RestaurantService {

  List<Restaurant> getAllRestaurants();

  Restaurant getRestaurantById(UUID restaurantId);

  Restaurant createRestaurant(Restaurant restaurant);

  Restaurant updateRestaurant(UUID restaurantId, RestaurantUpdateRequest restaurantUpdateRequest);

  void deleteRestaurant(UUID restaurantId);
}
