package com.food.ordering.app.kitchen.service.repository.projection;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface KitchenTicketDetailsView {
  UUID getId();
  LocalDateTime getCreatedAt();
  KitchenTicketStatus getStatus();
  BigDecimal getTotalPrice();
  List<KitchenTicketItemDetailsView> getTicketItems();
}
