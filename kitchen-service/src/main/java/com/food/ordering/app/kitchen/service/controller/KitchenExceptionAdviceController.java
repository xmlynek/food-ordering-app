package com.food.ordering.app.kitchen.service.controller;

import com.food.ordering.app.common.exception.ErrorResponse;
import com.food.ordering.app.common.exception.ExceptionAdviceController;
import com.food.ordering.app.kitchen.service.exception.IllegalKitchenTicketStatusException;
import com.food.ordering.app.kitchen.service.exception.KitchenTicketNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class KitchenExceptionAdviceController extends ExceptionAdviceController {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({KitchenTicketNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleOrderTicketNotFoundException(
      KitchenTicketNotFoundException ex) {
    log.warn("Handling OrderTicketNotFoundException: {}", ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
        HttpStatus.NOT_FOUND);
  }


  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(
      AccessDeniedException ex) {
    log.warn("Handling AccessDeniedException: {}", ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage()),
        HttpStatus.FORBIDDEN);
  }


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({IllegalKitchenTicketStatusException.class})
  public ResponseEntity<ErrorResponse> handleIllegalKitchenTicketStatusException(
      IllegalKitchenTicketStatusException ex) {
    log.warn("Handling IllegalKitchenTicketStatusException: {}", ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

}
