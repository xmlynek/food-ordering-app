package com.food.ordering.app.delivery.service.controller;

import com.food.ordering.app.common.exception.ErrorResponse;
import com.food.ordering.app.common.exception.ExceptionAdviceController;
import com.food.ordering.app.delivery.service.exception.DeliveryAlreadyAssignedException;
import com.food.ordering.app.delivery.service.exception.DeliveryNotFoundException;
import com.food.ordering.app.delivery.service.exception.IllegalDeliveryStatusException;
import com.food.ordering.app.delivery.service.exception.IllegalKitchenTicketStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class DeliveryExceptionAdviceController extends ExceptionAdviceController {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({DeliveryNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleDeliveryNotFoundException(
      DeliveryNotFoundException ex) {
    log.warn("Handling DeliveryNotFoundException: {}", ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({DeliveryAlreadyAssignedException.class, IllegalDeliveryStatusException.class,
      IllegalKitchenTicketStatusException.class})
  public ResponseEntity<ErrorResponse> handleDeliveryBadRequestException(
      RuntimeException ex) {
    log.warn("Handling {}: {}", ex.getClass().getName(), ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(
      AccessDeniedException ex) {
    log.warn("Handling AccessDeniedException: {}", ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage()),
        HttpStatus.FORBIDDEN);
  }

}
