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
  Page<KitchenTicket> findAllByRestaurantIdAndRestaurantOwnerId(@NonNull UUID restaurantId,
      @NonNull String ownerId, Pageable pageable);

  @NonNull
  <T> Optional<T> findByIdAndRestaurantIdAndRestaurantOwnerId(@NonNull UUID id,
      @NonNull UUID restaurantId, @NonNull String ownerId, Class<T> type);
}
