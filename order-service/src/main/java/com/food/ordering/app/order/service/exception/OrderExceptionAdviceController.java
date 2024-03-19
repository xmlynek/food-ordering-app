package com.food.ordering.app.order.service.exception;

import com.food.ordering.app.common.exception.ErrorResponse;
import com.food.ordering.app.common.exception.ExceptionAdviceController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class OrderExceptionAdviceController extends ExceptionAdviceController {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleOrderNotFoundException(
      OrderNotFoundException ex) {
    log.warn("Order not found exception: {}", ex.getMessage());
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
}
