package com.food.ordering.app.order.service.service;

import com.food.ordering.app.common.event.RestaurantRevisedEvent;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import com.food.ordering.app.order.service.entity.Restaurant;
import com.food.ordering.app.order.service.repository.RestaurantRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

  private final RestaurantRepository restaurantRepository;


  @Override
  public Restaurant createRestaurant(Restaurant restaurant) {
    return restaurantRepository.save(restaurant);
  }

  @Override
  @CacheEvict(value = "orders", allEntries = true, beforeInvocation = true)
  public Restaurant reviseRestaurant(UUID restaurantId,
      RestaurantRevisedEvent restaurantRevisedEvent) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

    restaurant.setIsAvailable(restaurantRevisedEvent.isAvailable());
    restaurant.setName(restaurantRevisedEvent.name());
    return restaurantRepository.save(restaurant);
  }

  @Override
  public void deleteRestaurant(UUID id) {
    Restaurant restaurant = restaurantRepository.findById(id)
        .orElseThrow(() -> new RestaurantNotFoundException(id));
    restaurant.setIsAvailable(false);
    restaurant.setIsDeleted(true);
    restaurantRepository.save(restaurant);
  }
}
