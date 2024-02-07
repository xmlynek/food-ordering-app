package com.food.ordering.app.order.service.repository;

import com.food.ordering.app.order.service.entity.OrderItem;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

}
