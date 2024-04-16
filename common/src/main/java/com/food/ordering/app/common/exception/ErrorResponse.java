package com.food.ordering.app.common.exception;

import java.time.ZonedDateTime;
import java.util.List;

public record ErrorResponse(int errorCode, List<String> errors, ZonedDateTime timestamp) {

  public ErrorResponse(int errorCode, String error) {
    this(errorCode, List.of(error), ZonedDateTime.now());
  }

  public ErrorResponse(int errorCode, List<String> errors) {
    this(errorCode, errors, ZonedDateTime.now());
  }
}
