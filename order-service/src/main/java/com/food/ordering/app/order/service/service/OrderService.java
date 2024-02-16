package com.food.ordering.app.order.service.service;

import com.food.ordering.app.common.event.OrderCreatedEvent;
import com.food.ordering.app.order.service.entity.Order;
import java.util.List;
import java.util.UUID;

public interface OrderService {

  Order createOrder(OrderCreatedEvent orderCreatedEvent);

  Order findByOrderId(UUID orderId);

  List<Order> findAllByCustomerId(UUID customerId);

}
