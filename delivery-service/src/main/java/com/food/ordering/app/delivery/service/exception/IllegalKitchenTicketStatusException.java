package com.food.ordering.app.delivery.service.exception;

public class IllegalKitchenTicketStatusException extends RuntimeException {
   public static final String ILLEGAL_KITCHEN_TICKET_STATUS_EXCEPTION_MESSAGE = "Illegal kitchen ticket status %s to perform %s operation";

   public IllegalKitchenTicketStatusException(String message, String operationName) {
      super(String.format(ILLEGAL_KITCHEN_TICKET_STATUS_EXCEPTION_MESSAGE, message, operationName));
   }
}
