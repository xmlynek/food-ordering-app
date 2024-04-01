package com.food.ordering.app.delivery.service.exception;

import java.util.UUID;

public class DeliveryAlreadyAssignedException extends RuntimeException {

  public static final String DELIVERY_ALREADY_ASSIGNED_EXCEPTION_MESSAGE = "Delivery is already assigned to other courier with id %s";

  public DeliveryAlreadyAssignedException(UUID courierId) {
    super(String.format(DELIVERY_ALREADY_ASSIGNED_EXCEPTION_MESSAGE, courierId.toString()));
  }

}
