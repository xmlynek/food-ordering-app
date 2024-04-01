package com.food.ordering.app.delivery.service.exception;

public class IllegalDeliveryStatusException extends RuntimeException {
  public static final String ILLEGAL_DELIVERY_STATUS_EXCEPTION_MESSAGE = "Illegal delivery status %s to perform %s operation";

  public IllegalDeliveryStatusException(String message, String operationName) {
    super(String.format(ILLEGAL_DELIVERY_STATUS_EXCEPTION_MESSAGE, message, operationName));
  }
}
