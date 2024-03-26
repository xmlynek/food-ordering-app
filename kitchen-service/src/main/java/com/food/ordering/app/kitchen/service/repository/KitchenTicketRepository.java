package com.food.ordering.app.kitchen.service.repository;

import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface KitchenTicketRepository extends JpaRepository<KitchenTicket, UUID> {

  @NonNull
//  @PostFilter("isFullyAuthenticated() and filterObject.restaurant.ownerId == authentication.name")
  List<KitchenTicket> findAllByRestaurantId(@NonNull UUID restaurantId);

  @NonNull
//  @PostAuthorize("isFullyAuthenticated() and (returnObject.isEmpty() or returnObject.get().restaurant.ownerId == authentication.name)")
  Optional<KitchenTicket> findByRestaurantIdAndId(@NonNull UUID restaurantId,
      @NonNull UUID orderId);

}
