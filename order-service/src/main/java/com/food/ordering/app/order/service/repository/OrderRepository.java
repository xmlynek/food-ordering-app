package com.food.ordering.app.order.service.repository;

import com.food.ordering.app.order.service.entity.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface OrderRepository extends JpaRepository<Order, UUID> {

  Page<Order> findAllByCustomerId(UUID customerId, Pageable pageable);

  @PreAuthorize("isFullyAuthenticated() and #customerId.toString() == authentication.name")
  Optional<Order> findByIdAndCustomerId(UUID id, UUID customerId);

  Optional<Order> findByKitchenTicketId(UUID ticketId);

}
