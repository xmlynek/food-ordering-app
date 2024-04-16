package com.food.ordering.app.common.exception;

import java.util.UUID;

public class MenuItemNotFoundException extends RuntimeException {

  public static final String MENU_ITEM_NOT_FOUND_EXCEPTION_MESSAGE = "Menu item with id '%s' not found";

  public MenuItemNotFoundException(UUID id) {
    super(String.format(MENU_ITEM_NOT_FOUND_EXCEPTION_MESSAGE, id.toString()));
  }

  public MenuItemNotFoundException(String id) {
    super(String.format(MENU_ITEM_NOT_FOUND_EXCEPTION_MESSAGE, id));
  }
}
