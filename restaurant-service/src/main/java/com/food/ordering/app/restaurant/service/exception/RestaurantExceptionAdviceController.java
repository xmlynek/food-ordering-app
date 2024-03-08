package com.food.ordering.app.restaurant.service.exception;

import com.food.ordering.app.common.exception.ErrorResponse;
import com.food.ordering.app.common.exception.ExceptionAdviceController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class RestaurantExceptionAdviceController extends ExceptionAdviceController {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({RestaurantNotFoundException.class, MenuItemNotFoundException.class,
      OrderTicketNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleNotFoundException(
      RuntimeException ex) {
    log.warn("Handling not found exception: {}", ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({InvalidImageTypeException.class})
  public ResponseEntity<ErrorResponse> handleBadRequestException(
      RuntimeException ex) {
    log.warn("Handling bad request exception: {}", ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

}
