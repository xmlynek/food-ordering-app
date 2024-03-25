package com.food.ordering.app.order.service.service;

import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.entity.OrderStatus;
import com.food.ordering.app.order.service.exception.OrderNotFoundException;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * This interface defines the business logic for managing orders.
 */
public interface OrderService {

  /**
   * Creates a new order.
   *
   * @param order the order to create
   * @return the created order
   */
  Order createOrder(Order order);

  /**
   * Finds an order by its ID.
   *
   * @param orderId the ID of the order to find
   * @return the order with the given ID, or null if no order with that ID exists
   * @throws OrderNotFoundException if no order with the given ID exists
   */
  Order findByOrderId(UUID orderId) throws OrderNotFoundException;

  /**
   * Finds all orders for a given customer.
   *
   * @param customerId the ID of the customer
   * @param pageable pagination object
   * @return a page of orders for the given customer
   */
  Page<Order> findAllByCustomerId(UUID customerId, Pageable pageable);


  void updateOrderStatus(UUID orderId, OrderStatus orderStatus);

}
