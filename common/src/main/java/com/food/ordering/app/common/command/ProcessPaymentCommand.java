package com.food.ordering.app.common.command;


import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
@AllArgsConstructor
public class ProcessPaymentCommand {

  @TargetAggregateIdentifier
  private final UUID paymentId;
  private final UUID orderId;
  private final UUID customerId;
  private final BigDecimal price;

}
