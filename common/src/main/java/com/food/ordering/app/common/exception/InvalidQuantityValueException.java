package com.food.ordering.app.common.exception;

import java.util.UUID;

public class InvalidQuantityValueException extends RuntimeException {

  public static final String INVALID_QUANTITY_VALUE_EXCEPTION_MESSAGE = "Invalid quantity value '%s' for product '%s'";

  public InvalidQuantityValueException(int quantity, UUID productId) {
    super(String.format(INVALID_QUANTITY_VALUE_EXCEPTION_MESSAGE, quantity, productId.toString()));
  }

}
