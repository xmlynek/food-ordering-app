package com.food.ordering.app.order.service.service;

import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.entity.OrderStatus;
import com.food.ordering.app.order.service.exception.OrderNotFoundException;
import com.food.ordering.app.order.service.repository.OrderRepository;
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
}
