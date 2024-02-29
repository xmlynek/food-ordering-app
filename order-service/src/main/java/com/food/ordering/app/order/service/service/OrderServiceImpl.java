package com.food.ordering.app.order.service.service;

import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.entity.OrderStatus;
import com.food.ordering.app.order.service.exception.OrderNotFoundException;
import com.food.ordering.app.order.service.repository.OrderItemRepository;
import com.food.ordering.app.order.service.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  @Override
  @Transactional
  public Order createOrder(Order order) {
    order.setOrderStatus(OrderStatus.PENDING);

    order.getItems().forEach(orderItem -> {
      orderItem.setOrder(order);
      orderItem.setTotalPrice(
          orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
    });

    orderItemRepository.saveAll(order.getItems());

    return orderRepository.save(order);
  }

  @Override
  public Order findByOrderId(UUID orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));
  }

  @Override
  public List<Order> findAllByCustomerId(UUID customerId) {
    return orderRepository.findAllByCustomerId(customerId);
  }

  @Override
  @Transactional
  public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));
    order.setOrderStatus(orderStatus);
  }
}
