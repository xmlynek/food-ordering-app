package com.food.ordering.app.kitchen.service.repository;

import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface KitchenTicketRepository extends JpaRepository<KitchenTicket, UUID> {

  @NonNull
//  @PostFilter("isFullyAuthenticated() and filterObject.restaurant.ownerId == authentication.name")
  Page<KitchenTicket> findAllByRestaurantId(@NonNull UUID restaurantId, Pageable pageable);

//  @PostAuthorize("isFullyAuthenticated() and (returnObject.isEmpty() or returnObject.get().restaurant.ownerId == authentication.name)")
  @NonNull
  <T> Optional<T> findByIdAndRestaurantId(@NonNull UUID id, @NonNull UUID restaurantId, Class<T> type);
}
