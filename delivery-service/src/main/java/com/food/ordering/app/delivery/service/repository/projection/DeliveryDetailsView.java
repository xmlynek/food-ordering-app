package com.food.ordering.app.delivery.service.repository.projection;

import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.model.Address;
import java.time.LocalDateTime;
import java.util.UUID;

public interface DeliveryDetailsView {

  UUID getId();
  UUID getCustomerId();
  UUID getCourierId();
  KitchenTicketStatus getKitchenTicketStatus();
  LocalDateTime getLastModifiedAt();
  DeliveryStatus getDeliveryStatus();
  Address getDeliveryAddress();
  RestaurantView getRestaurant();

  interface RestaurantView {
    UUID getId();
    String getName();
    Address getAddress();
  }
}
