package com.food.ordering.app.order.service.repository.projection;

import java.util.UUID;

public interface OrderMenuItemDetailsView {

  UUID getId();

  String getImageUrl();

  String getName();

  String getDescription();
}
