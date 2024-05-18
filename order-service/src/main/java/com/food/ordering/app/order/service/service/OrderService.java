package com.food.ordering.app.order.service.service;

import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.order.service.dto.OrderDetails;
import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.entity.OrderStatus;
import com.food.ordering.app.order.service.exception.OrderNotFoundException;
import java.util.List;
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

  OrderDetails getOrderDetailsById(UUID orderId) throws OrderNotFoundException;

  /**
   * Finds all orders for a given customer.
   *
   * @param customerId the ID of the customer
   * @param pageable pagination object
   * @return a page of orders for the given customer
   */
  Page<Order> findAllByCustomerId(UUID customerId, Pageable pageable);


  void updateOrderStatus(UUID orderId, OrderStatus orderStatus);

  void setFailureMessages(UUID orderId, List<String> failureMessages);

  void updateKitchenTicketStatus(UUID ticketId, KitchenTicketStatus kitchenTicketStatus);

  void updateKitchenTicketData(UUID orderId, UUID ticketId, KitchenTicketStatus kitchenTicketStatus);

  void updateOrderDeliveryData(UUID orderId, UUID deliveryId, DeliveryStatus deliveryStatus);

}
