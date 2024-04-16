package com.food.ordering.app.common.exception;

import java.util.UUID;

public class RestaurantNotFoundException extends RuntimeException {

  public static final String RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE = "Restaurant with id '%s' not found";

  public RestaurantNotFoundException(String id) {
    super(String.format(RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE, id));
  }

  public RestaurantNotFoundException(UUID id) {
    super(String.format(RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE, id.toString()));
  }
}
