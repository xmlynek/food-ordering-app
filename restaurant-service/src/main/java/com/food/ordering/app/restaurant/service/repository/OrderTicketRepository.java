package com.food.ordering.app.restaurant.service.repository;

import com.food.ordering.app.restaurant.service.entity.RestaurantOrderTicket;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTicketRepository extends JpaRepository<RestaurantOrderTicket, UUID> {

  List<RestaurantOrderTicket> findAllByRestaurantId(UUID restaurantId);

  Optional<RestaurantOrderTicket> findByRestaurantIdAndId(UUID restaurantId, UUID orderId);

}
