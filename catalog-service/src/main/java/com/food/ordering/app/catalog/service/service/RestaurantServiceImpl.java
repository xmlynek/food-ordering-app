package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.catalog.service.repository.RestaurantRepository;
import com.food.ordering.app.common.event.RestaurantRevisedEvent;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

  private final RestaurantRepository restaurantRepository;


  @Override
  public Mono<Restaurant> createRestaurant(Restaurant restaurant) {
    return restaurantRepository.save(restaurant);
  }

  @Override
  public Mono<Restaurant> reviseRestaurant(String restaurantId,
      RestaurantRevisedEvent restaurantRevisedEvent) {
    return restaurantRepository.findById(restaurantId)
        .flatMap(restaurant -> {
          restaurant.setIsAvailable(restaurantRevisedEvent.isAvailable());
          restaurant.setName(restaurantRevisedEvent.name());
          restaurant.setLastModifiedAt(restaurantRevisedEvent.lastModifiedAt());
          restaurant.setAddress(restaurantRevisedEvent.address());
          return restaurantRepository.save(restaurant);
        }).switchIfEmpty(
            Mono.error(new RestaurantNotFoundException(restaurantId)));
  }

  @Override
  public Mono<Void> deleteRestaurant(String id) {
    return restaurantRepository.existsById(id)
        .flatMap(exists -> {
          if (Boolean.TRUE.equals(exists)) {
            return restaurantRepository.deleteById(id);
          } else {
            return Mono.error(new RestaurantNotFoundException(id));
          }
        });
  }
}
