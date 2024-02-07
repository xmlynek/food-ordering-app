package com.food.ordering.app.order.repository;

import com.food.ordering.app.order.entity.Order;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {

  List<Order> findAllByCustomerId(UUID customerId);

//  Optional<Order> findByCustomerIdAndId(UUID customerId, UUID id);

}
