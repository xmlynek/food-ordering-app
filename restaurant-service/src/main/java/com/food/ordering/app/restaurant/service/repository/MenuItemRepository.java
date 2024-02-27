package com.food.ordering.app.restaurant.service.repository;

import com.food.ordering.app.restaurant.service.entity.MenuItem;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

  List<MenuItem> findByRestaurantId(UUID restaurantId);

  List<MenuItem> findByRestaurantIdAndIsDeletedFalse(UUID restaurantId);

  Optional<MenuItem> findByIdAndRestaurantId(UUID menuId, UUID restaurantId);
}
