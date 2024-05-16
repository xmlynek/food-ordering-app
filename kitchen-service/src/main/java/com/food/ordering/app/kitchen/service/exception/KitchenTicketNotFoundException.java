package com.food.ordering.app.kitchen.service.exception;

import java.util.UUID;

public class KitchenTicketNotFoundException extends RuntimeException {

  public static final String KITCHEN_TICKET_NOT_FOUND_IN_RESTAURANT_EXCEPTION_MESSAGE = "Kitchen ticket with id '%s' not found in restaurant with id '%s'";
  public static final String KITCHEN_TICKET_NOT_FOUND_EXCEPTION_MESSAGE = "Kitchen ticket with id '%s' not found";
  public static final String KITCHEN_TICKET_WITH_DELIVERY_ID_NOT_FOUND_EXCEPTION_MESSAGE = "Kitchen ticket with id '%s' and deliveryId '%s' not found";

  public KitchenTicketNotFoundException(UUID restaurantId, UUID ticketId) {
    super(String.format(KITCHEN_TICKET_NOT_FOUND_IN_RESTAURANT_EXCEPTION_MESSAGE, ticketId.toString(),
        restaurantId.toString()));
  }

  public KitchenTicketNotFoundException(UUID ticketId) {
    super(String.format(KITCHEN_TICKET_NOT_FOUND_EXCEPTION_MESSAGE, ticketId.toString()));
  }

  public KitchenTicketNotFoundException(String message) {
    super(message);
  }
}
