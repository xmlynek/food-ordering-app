package com.food.ordering.app.kitchen.service.repository;

import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

public interface KitchenTicketRepository extends JpaRepository<KitchenTicket, UUID>,
    JpaSpecificationExecutor<KitchenTicket> {

  @NonNull
  <T> Optional<T> findByIdAndRestaurantIdAndRestaurantOwnerId(@NonNull UUID id,
      @NonNull UUID restaurantId, @NonNull String ownerId, Class<T> type);

  @NonNull
  Optional<KitchenTicket> findByIdAndDeliveryId(@NonNull UUID id, @NonNull UUID deliveryId);
}
