package com.food.ordering.app.order.service.exception;

import java.util.UUID;

/**
 * Exception class for when an order with a specific ID cannot be found.
 */
public class OrderNotFoundException extends RuntimeException {

  /**
   * Constant for the exception message.
   */
  public static final String ORDER_NOT_FOUND_EXCEPTION_MESSAGE = "Order with id '%s' not found";

  /**
   * Constructs a new OrderNotFoundException with the given order ID.
   *
   * @param id the ID of the order that was not found
   */
  public OrderNotFoundException(UUID id) {
    super(String.format(ORDER_NOT_FOUND_EXCEPTION_MESSAGE, id));
  }

  public OrderNotFoundException(String message) {
    super(message);
  }
}
