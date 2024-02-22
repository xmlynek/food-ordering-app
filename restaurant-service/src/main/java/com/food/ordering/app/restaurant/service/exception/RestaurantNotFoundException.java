package com.food.ordering.app.restaurant.service.exception;

import java.util.UUID;

public class RestaurantNotFoundException extends RuntimeException {

  public static final String RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE = "Restaurant with id '%s' not found";

  public RestaurantNotFoundException(UUID id) {
    super(String.format(RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE, id));
  }
}
