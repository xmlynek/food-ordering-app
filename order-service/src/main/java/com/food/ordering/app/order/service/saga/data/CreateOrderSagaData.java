package com.food.ordering.app.order.service.saga.data;

import com.food.ordering.app.common.model.OrderProduct;
import com.food.ordering.app.order.service.entity.Address;
import com.food.ordering.app.order.service.entity.OrderStatus;
import io.eventuate.examples.common.money.Money;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class CreateOrderSagaData {

  private UUID orderId;

  private UUID customerId;

  private UUID restaurantId;

  private UUID paymentId;

  private OrderStatus orderStatus;

  private Money price;

  @Builder.Default
  private List<OrderProduct> items = new ArrayList<>();

  private Address address;

  @Builder.Default
  private List<String> failureMessages = new ArrayList<>();
}
