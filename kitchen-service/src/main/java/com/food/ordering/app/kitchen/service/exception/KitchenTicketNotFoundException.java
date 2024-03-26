package com.food.ordering.app.kitchen.service.exception;

import java.util.UUID;

public class KitchenTicketNotFoundException extends RuntimeException {

  public static final String ORDER_TICKET_NOT_FOUND_EXCEPTION_MESSAGE = "Order ticket with id '%s' not found in restaurant with id '%s'";

  public KitchenTicketNotFoundException(UUID restaurantId, UUID orderId) {
    super(String.format(ORDER_TICKET_NOT_FOUND_EXCEPTION_MESSAGE, orderId.toString(),
        restaurantId.toString()));
  }

}
