package com.food.ordering.app.common.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InvalidPriceValueException extends RuntimeException {

  public static final String INVALID_PRICE_VALUE_FOR_PRODUCT_EXCEPTION_MESSAGE = "Invalid price value '%s' for product '%s'";
  public static final String INVALID_TOTAL_PRICE_VALUE_EXCEPTION_MESSAGE = "Invalid total price value '%s'";

  public InvalidPriceValueException(BigDecimal price, UUID productId) {
    super(String.format(INVALID_PRICE_VALUE_FOR_PRODUCT_EXCEPTION_MESSAGE, price, productId.toString()));
  }

  public InvalidPriceValueException(BigDecimal price) {
    super(String.format(INVALID_TOTAL_PRICE_VALUE_EXCEPTION_MESSAGE, price));
  }

}
