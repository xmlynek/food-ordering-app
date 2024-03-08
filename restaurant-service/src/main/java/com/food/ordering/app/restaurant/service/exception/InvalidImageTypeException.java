package com.food.ordering.app.restaurant.service.exception;

public class InvalidImageTypeException extends RuntimeException {
  public static final String INVALID_IMAGE_TYPE_MESSAGE = "Invalid image type. Accepted image types are %s";

  public InvalidImageTypeException(String acceptedImageTypes) {
    super(INVALID_IMAGE_TYPE_MESSAGE);
  }
}
