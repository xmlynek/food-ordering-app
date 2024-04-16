package com.food.ordering.app.order.service.exception;

public class InvalidOrderRequestDataException extends RuntimeException {
  public static final String INVALID_ORDER_REQUEST_DATA_EXCEPTION_MESSAGE = "Invalid order request data: %s";

  public InvalidOrderRequestDataException(String message) {
    super(String.format(INVALID_ORDER_REQUEST_DATA_EXCEPTION_MESSAGE, message));
  }
}
