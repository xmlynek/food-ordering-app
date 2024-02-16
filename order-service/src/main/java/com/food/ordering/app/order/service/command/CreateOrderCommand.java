package com.food.ordering.app.order.service.command;

import com.food.ordering.app.common.dto.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class CreateOrderCommand {

  @TargetAggregateIdentifier
  private final UUID orderId;
  private final UUID customerId;
  private final UUID restaurantId;
  private final BigDecimal price;
  private final String city;
  private final String postalCode;
  private final String street;
  private final List<Product> items;

}
