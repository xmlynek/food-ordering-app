package com.food.ordering.app.order.service.repository;

import com.food.ordering.app.order.service.entity.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {

  Page<Order> findAllByCustomerId(UUID customerId, Pageable pageable);

//  Optional<Order> findByCustomerIdAndId(UUID customerId, UUID id);

  Optional<Order> findByKitchenTicketId(UUID ticketId);

}
