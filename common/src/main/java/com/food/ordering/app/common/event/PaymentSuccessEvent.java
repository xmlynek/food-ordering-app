package com.food.ordering.app.common.event;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PaymentSuccessEvent {

  private final UUID paymentId;
  private final UUID orderId;
  private final UUID customerId;
  private final BigDecimal price;

}
