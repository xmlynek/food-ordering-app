package com.food.ordering.app.restaurant.service.exception;

import java.util.UUID;

public class MenuItemNotAvailableException extends RuntimeException {
  public static final String MENU_ITEM_NOT_AVAILABLE_EXCEPTION_MESSAGE = "Menu item with id '%s' not available";

  public MenuItemNotAvailableException(UUID menuItemId) {
    super(String.format(MENU_ITEM_NOT_AVAILABLE_EXCEPTION_MESSAGE, menuItemId.toString()));
  }
}
