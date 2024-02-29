package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.restaurant.service.dto.RestaurantRequest;
import com.food.ordering.app.restaurant.service.entity.Restaurant;
import com.food.ordering.app.restaurant.service.exception.RestaurantNotFoundException;
import com.food.ordering.app.restaurant.service.repository.RestaurantRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
  private final RestaurantRepository restaurantRepository;


  @Transactional(readOnly = true)
  @Override
  public List<Restaurant> getAllRestaurants() {
    // TODO: add pagination?
    return restaurantRepository.findAll();
  }

//  @Transactional(readOnly = true)
  @Override
  public Restaurant getRestaurantById(UUID restaurantId) {
    // TODO: projection?
    return restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
  }

  @Transactional
  @Override
  public Restaurant createRestaurant(Restaurant restaurant) {
    // TODO: publish event for CQRS
    restaurant.setIsDeleted(false);
    restaurant.setIsAvailable(true);

    return restaurantRepository.save(restaurant);
  }

  @Transactional
  @Override
  public Restaurant updateRestaurant(UUID restaurantId, RestaurantRequest restaurantRequest) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    restaurant.setName(restaurantRequest.name());

    return restaurantRepository.save(restaurant);
  }

  @Transactional
  @Override
  public void deleteRestaurant(UUID restaurantId) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

    // soft delete
    restaurant.setIsDeleted(true);
//    restaurantRepository.delete(restaurant);
  }
}
