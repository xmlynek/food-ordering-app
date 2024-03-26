package com.food.ordering.app.kitchen.service.repository.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface KitchenTicketItemDetailsView {
  UUID getId();
  Integer getQuantity();
  BigDecimal getPrice();
  MenuItemView getMenuItem();

  interface MenuItemView {
    UUID getId();
    String getName();
    String getImageUrl();
  }
}

