package com.food.ordering.app.command;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class CreateOrderCommand {

//  @TargetAggregateIdentifier
//  private final UUID orderId;
  private final UUID customerId;
  private final UUID restaurantId;
  private final BigDecimal price;

}
