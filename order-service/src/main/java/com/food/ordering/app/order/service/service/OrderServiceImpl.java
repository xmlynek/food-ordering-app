package com.food.ordering.app.order.service.service;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.entity.OrderStatus;
import com.food.ordering.app.order.service.exception.OrderNotFoundException;
import com.food.ordering.app.order.service.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  @Transactional
  public Order createOrder(Order order) {
    order.setOrderStatus(OrderStatus.PENDING);

    order.getItems().forEach(orderItem -> orderItem.setOrder(order));

    return orderRepository.save(order);
  }

  @Override
  public Order findByOrderId(UUID orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));
  }

  @Override
  public Page<Order> findAllByCustomerId(UUID customerId, Pageable pageable) {
    return orderRepository.findAllByCustomerId(customerId, pageable);
  }

  @Override
  @Transactional
  public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));
    order.setOrderStatus(orderStatus);
  }

  @Override
  @Transactional
  public void setFailureMessages(UUID orderId, List<String> failureMessages) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));
    order.getFailureMessages().addAll(failureMessages);
  }

  @Override
  @Transactional
  public void updateKitchenTicketStatus(UUID ticketId, KitchenTicketStatus kitchenTicketStatus) {
    Order order = orderRepository.findByKitchenTicketId(ticketId)
        .orElseThrow(() -> new OrderNotFoundException(ticketId));
    order.setKitchenTicketStatus(kitchenTicketStatus);
  }

  @Override
  @Transactional
  public void setTicketId(UUID orderId, UUID ticketId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));
    order.setKitchenTicketId(ticketId);
  }
}
