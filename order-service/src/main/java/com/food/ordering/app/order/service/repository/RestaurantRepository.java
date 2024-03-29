package com.food.ordering.app.order.service.repository;

import com.food.ordering.app.order.service.entity.Restaurant;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

  boolean existsByIdAndIsDeletedFalseAndIsAvailableTrue(@NonNull UUID uuid);
}
