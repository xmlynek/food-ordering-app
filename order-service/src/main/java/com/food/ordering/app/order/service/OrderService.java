package com.food.ordering.app.order.service;

import com.food.ordering.app.order.entity.Order;
import java.util.List;
import java.util.UUID;

public interface OrderService {

  Order createOrder(Order order);

  Order findByOrderId(UUID orderId);

  List<Order> findAllByCustomerId(UUID customerId);

}
