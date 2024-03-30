package com.food.ordering.app.delivery.service.controller;

import com.food.ordering.app.common.exception.ErrorResponse;
import com.food.ordering.app.common.exception.ExceptionAdviceController;
import com.food.ordering.app.delivery.service.exception.DeliveryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//  @ResponseStatus(HttpStatus.FORBIDDEN)
//  @ExceptionHandler(AccessDeniedException.class)
//  public ResponseEntity<ErrorResponse> handleAccessDeniedException(
//      AccessDeniedException ex) {
//    log.warn("Handling AccessDeniedException: {}", ex.getMessage());
//    return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage()),
//        HttpStatus.FORBIDDEN);
//  }

}
