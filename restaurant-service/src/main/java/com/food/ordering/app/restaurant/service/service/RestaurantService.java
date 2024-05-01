package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.restaurant.service.dto.RestaurantUpdateRequest;
import com.food.ordering.app.restaurant.service.entity.Restaurant;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {

  Page<Restaurant> getAllRestaurants(Pageable pageable);

  Restaurant getRestaurantById(UUID restaurantId);

  Restaurant createRestaurant(Restaurant restaurant);

  Restaurant updateRestaurant(UUID restaurantId, RestaurantUpdateRequest restaurantUpdateRequest);

  void deleteRestaurant(UUID restaurantId);
}
