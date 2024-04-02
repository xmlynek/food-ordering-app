package com.food.ordering.app.delivery.service.repository;

import com.food.ordering.app.delivery.service.entity.Restaurant;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

}
