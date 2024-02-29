package com.food.ordering.app.restaurant.service.repository;

import com.food.ordering.app.restaurant.service.entity.OrderTicketItem;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTicketItemRepository extends JpaRepository<OrderTicketItem, UUID> {

}
