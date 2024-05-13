package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.common.event.RestaurantCreatedEvent;
import com.food.ordering.app.common.event.RestaurantDeletedEvent;
import com.food.ordering.app.common.event.RestaurantRevisedEvent;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import com.food.ordering.app.restaurant.service.dto.RestaurantUpdateRequest;
import com.food.ordering.app.restaurant.service.entity.Restaurant;
import com.food.ordering.app.restaurant.service.event.publisher.RestaurantDomainEventPublisher;
import com.food.ordering.app.restaurant.service.mapper.AddressMapper;
import com.food.ordering.app.restaurant.service.mapper.RestaurantMapper;
import com.food.ordering.app.restaurant.service.principal.PrincipalProvider;
import com.food.ordering.app.restaurant.service.repository.RestaurantRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final RestaurantDomainEventPublisher domainEventPublisher;
  private final RestaurantMapper restaurantMapper;
  private final AddressMapper addressMapper;
  private final PrincipalProvider principalProvider;


  @Transactional(readOnly = true)
  @Override
  public Page<Restaurant> getAllRestaurants(Pageable pageable) {
    return restaurantRepository.findAllByOwnerIdAndIsDeletedFalse(principalProvider.getName(),
        pageable);
  }

  @Transactional(readOnly = true)
  @Override
  public Restaurant getRestaurantById(UUID restaurantId) {
    return restaurantRepository.findByIdAndIsDeletedFalse(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
  }

  @Override
  public Restaurant createRestaurant(Restaurant restaurant) {
    restaurant.setIsDeleted(false);
    restaurant.setCreatedAt(LocalDateTime.now());
    restaurant.setIsAvailable(
        true); // TODO: set to false by default until the restaurant contains some menus?
    restaurant.setLastModifiedAt(LocalDateTime.now());
    restaurant.setOwnerId(principalProvider.getName());

    Restaurant createdRestaurant = restaurantRepository.save(restaurant);

    RestaurantCreatedEvent restaurantCreatedEvent = restaurantMapper.restaurantEntityToRestaurantCreateEvent(
        createdRestaurant);

    domainEventPublisher.publish(createdRestaurant,
        Collections.singletonList(restaurantCreatedEvent));

    return createdRestaurant;
  }

  @Override
  public Restaurant updateRestaurant(UUID restaurantId,
      RestaurantUpdateRequest restaurantUpdateRequest) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    restaurant.setName(restaurantUpdateRequest.name());
    restaurant.setLastModifiedAt(LocalDateTime.now());
    restaurant.setIsAvailable(restaurantUpdateRequest.isAvailable());
    restaurant.setDescription(restaurantUpdateRequest.description());
    restaurant.setAddress(addressMapper.addressDtoToAddress(restaurantUpdateRequest.address()));

    Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

    RestaurantRevisedEvent restaurantRevisedEvent = restaurantMapper.restaurantEntityToRestaurantRevisedEvent(
        updatedRestaurant);

    domainEventPublisher.publish(updatedRestaurant,
        Collections.singletonList(restaurantRevisedEvent));
    return updatedRestaurant;
  }

  @Override
  public void deleteRestaurant(UUID restaurantId) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

    // soft delete
    restaurant.setIsDeleted(true);
    restaurant.setIsAvailable(false);
    restaurant.setLastModifiedAt(LocalDateTime.now());

    domainEventPublisher.publish(restaurant,
        Collections.singletonList(new RestaurantDeletedEvent()));
  }
}
