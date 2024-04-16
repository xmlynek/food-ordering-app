package com.food.ordering.app.kitchen.service.repository;

import com.food.ordering.app.kitchen.service.entity.MenuItem;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface RestaurantMenuItemRepository extends JpaRepository<MenuItem, UUID> {

  @NonNull
  Optional<MenuItem> findByIdAndRestaurantIdAndIsDeletedFalseAndIsAvailableTrue(
      @NonNull UUID menuId, @NonNull UUID restaurantId);
}
