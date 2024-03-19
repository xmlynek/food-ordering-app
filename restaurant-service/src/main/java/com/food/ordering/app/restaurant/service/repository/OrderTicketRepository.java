package com.food.ordering.app.restaurant.service.repository;

import com.food.ordering.app.restaurant.service.entity.RestaurantOrderTicket;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;

public interface OrderTicketRepository extends JpaRepository<RestaurantOrderTicket, UUID> {

  @NonNull
  @PostFilter("isFullyAuthenticated() and filterObject.restaurant.ownerId == authentication.name")
  List<RestaurantOrderTicket> findAllByRestaurantId(@NonNull UUID restaurantId);

  @NonNull
  @PostAuthorize("isFullyAuthenticated() and (returnObject.isEmpty() or returnObject.get().restaurant.ownerId == authentication.name)")
  Optional<RestaurantOrderTicket> findByRestaurantIdAndId(@NonNull UUID restaurantId,
      @NonNull UUID orderId);

}
