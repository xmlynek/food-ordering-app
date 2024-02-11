package com.food.ordering.app.restaurant.service.repository;

import com.food.ordering.app.restaurant.service.entity.Restaurant;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

}
