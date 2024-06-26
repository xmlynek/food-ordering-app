package com.food.ordering.app.order.service.saga.data;

import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.enums.PaymentStatus;
import com.food.ordering.app.common.model.Address;
import com.food.ordering.app.common.model.OrderProduct;
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

  private UUID ticketId;

  private UUID deliveryId;

  private OrderStatus orderStatus;

  private KitchenTicketStatus kitchenTicketStatus;

  private PaymentStatus paymentStatus;

  private DeliveryStatus deliveryStatus;

  private String paymentToken;

  private Money totalPrice;

  @Builder.Default
  private List<OrderProduct> items = new ArrayList<>();

  private Address address;

  @Builder.Default
  private List<String> failureMessages = new ArrayList<>();
}
