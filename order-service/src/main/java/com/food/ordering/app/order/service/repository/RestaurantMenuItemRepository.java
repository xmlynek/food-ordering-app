package com.food.ordering.app.order.service.repository;

import com.food.ordering.app.order.service.entity.MenuItem;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface RestaurantMenuItemRepository extends JpaRepository<MenuItem, UUID> {

  boolean existsByIdAndRestaurantIdAndIsDeletedFalseAndIsAvailableTrueAndPriceEquals(
      @NonNull UUID menuId, @NonNull UUID restaurantId, @NonNull BigDecimal price);

  <T> Optional<T> findById(@NonNull UUID id, @NonNull Class<T> type);
}
