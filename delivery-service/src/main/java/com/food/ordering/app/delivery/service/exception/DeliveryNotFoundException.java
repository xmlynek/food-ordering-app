package com.food.ordering.app.delivery.service.exception;

import java.util.UUID;

public class DeliveryNotFoundException extends RuntimeException {

  public static final String DELIVERY_NOT_FOUND_EXCEPTION_MESSAGE = "Delivery not found with id: %s";

  public DeliveryNotFoundException(UUID id) {
    super(String.format(DELIVERY_NOT_FOUND_EXCEPTION_MESSAGE, id.toString()));
  }

  public DeliveryNotFoundException(String message) {
    super(message);
  }

}
