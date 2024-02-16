package com.food.ordering.app.order.service.service;

import com.food.ordering.app.common.event.OrderCreatedEvent;
import com.food.ordering.app.order.service.entity.Address;
import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.entity.OrderItem;
import com.food.ordering.app.order.service.entity.OrderStatus;
import com.food.ordering.app.order.service.repository.OrderItemRepository;
import com.food.ordering.app.order.service.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
  public Order createOrder(OrderCreatedEvent orderCreatedEvent) {
    Order order = Order.builder()
        .id(orderCreatedEvent.getOrderId())
        .orderStatus(OrderStatus.PENDING)
        .price(orderCreatedEvent.getPrice())
        .restaurantId(orderCreatedEvent.getRestaurantId())
        .customerId(orderCreatedEvent.getCustomerId())
        .address(Address.builder()
            .city(orderCreatedEvent.getCity())
            .street(orderCreatedEvent.getStreet())
            .postalCode(orderCreatedEvent.getPostalCode())
            .build())
        .build();

    Order savedOrder = orderRepository.save(order);
    List<OrderItem> orderItems = orderCreatedEvent.getItems().stream()
        .map(product -> OrderItem.builder()
            .order(savedOrder)
            .productId(product.getProductId())
            .quantity(product.getQuantity())
            .price(product.getPrice())
            .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
            .build()).collect(Collectors.toList());

    orderItemRepository.saveAll(orderItems);

    return savedOrder;
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
}
