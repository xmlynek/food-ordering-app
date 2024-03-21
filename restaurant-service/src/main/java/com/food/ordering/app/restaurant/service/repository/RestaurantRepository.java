package com.food.ordering.app.restaurant.service.repository;

import com.food.ordering.app.restaurant.service.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

  @NonNull
//  @PostFilter("isFullyAuthenticated() and (filterObject.ownerId == authentication.name)")
  List<Restaurant> findAllByOwnerIdAndIsDeletedFalse(@NonNull String ownerId);

  @Override
  @NonNull
  @PreAuthorize("isFullyAuthenticated() and #entity.ownerId == authentication.name")
  <S extends Restaurant> S save(@NonNull S entity);

  @NonNull
  @PostAuthorize("isFullyAuthenticated() and (returnObject.isEmpty() or returnObject.get().getOwnerId() == authentication.name)")
  Optional<Restaurant> findByIdAndIsDeletedFalse(@NonNull UUID uuid);
}
