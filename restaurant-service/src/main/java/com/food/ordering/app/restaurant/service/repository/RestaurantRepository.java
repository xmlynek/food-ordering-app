package com.food.ordering.app.restaurant.service.repository;

import com.food.ordering.app.restaurant.service.entity.Restaurant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

  @NonNull
//  @PostFilter("isFullyAuthenticated() and (filterObject.ownerId == authentication.name)")
  Page<Restaurant> findAllByOwnerIdAndIsDeletedFalse(@NonNull String ownerId, Pageable pageable);

  @Override
  @NonNull
  @PreAuthorize("isFullyAuthenticated() and #entity.ownerId == authentication.name")
  <S extends Restaurant> S save(@NonNull S entity);

  @NonNull
  @PostAuthorize("isFullyAuthenticated() and (returnObject.isEmpty() or returnObject.get().getOwnerId() == authentication.name)")
  Optional<Restaurant> findByIdAndIsDeletedFalse(@NonNull UUID uuid);

  @NonNull
  Optional<Restaurant> findByIdAndIsDeletedFalseAndIsAvailableTrue(@NonNull UUID uuid);
}
