package com.food.ordering.app.kitchen.service.repository;

import com.food.ordering.app.kitchen.service.entity.Restaurant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {



  @NonNull
//  @PostAuthorize("isFullyAuthenticated() and (returnObject.isEmpty() or returnObject.get().getOwnerId() == authentication.name)")
  Optional<Restaurant> findByIdAndIsDeletedFalse(@NonNull UUID uuid);

  @NonNull
  Optional<Restaurant> findByIdAndIsDeletedFalseAndIsAvailableTrue(@NonNull UUID uuid);

}
