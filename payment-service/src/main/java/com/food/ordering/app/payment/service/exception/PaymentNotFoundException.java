package com.food.ordering.app.payment.service.exception;

import java.util.UUID;

/**
 * This exception is thrown when a payment with a specific ID cannot be found in the system.
 */
public class PaymentNotFoundException extends RuntimeException {

  /**
   * This is the exception message that is displayed when a payment with a specific ID cannot be
   * found.
   */
  public static final String PAYMENT_NOT_FOUND_EXCEPTION_MESSAGE = "Payment with id '%s' not found";

  /**
   * This constructor takes in a payment ID and constructs the exception message using the
   * PAYMENT_NOT_FOUND_EXCEPTION_MESSAGE constant.
   *
   * @param id the ID of the payment that was not found
   */
  public PaymentNotFoundException(UUID id) {
    super(String.format(PAYMENT_NOT_FOUND_EXCEPTION_MESSAGE, id));
  }
}
