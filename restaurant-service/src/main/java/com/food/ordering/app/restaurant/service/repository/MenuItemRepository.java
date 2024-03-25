package com.food.ordering.app.restaurant.service.repository;

import com.food.ordering.app.restaurant.service.entity.MenuItem;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

  @NonNull
  @PostFilter("isFullyAuthenticated() and filterObject.restaurant.ownerId == authentication.name")
  List<MenuItem> findByRestaurantIdAndIsDeletedFalse(@NonNull UUID restaurantId);

  @Override
  @NonNull
  @PreAuthorize("isFullyAuthenticated() and #entity.restaurant.ownerId == authentication.name")
  <S extends MenuItem> S save(@NonNull S entity);

  @NonNull
  @PostAuthorize("isFullyAuthenticated() and (returnObject.isEmpty() or returnObject.get().getRestaurant().ownerId == authentication.name)")
  Optional<MenuItem> findByIdAndRestaurantIdAndIsDeletedFalse(@NonNull UUID menuId, @NonNull UUID restaurantId);

  @NonNull
  Optional<MenuItem> findByIdAndRestaurantIdAndIsDeletedFalseAndIsAvailableTrue(@NonNull UUID menuId, @NonNull UUID restaurantId);
}
