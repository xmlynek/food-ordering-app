package com.food.ordering.app.order.service.aggregate;

import com.food.ordering.app.common.event.OrderCreatedEvent;
import com.food.ordering.app.order.service.command.CreateOrderCommand;
import com.food.ordering.app.order.service.entity.OrderStatus;
import com.food.ordering.app.order.service.service.OrderService;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

  @AggregateIdentifier
  private UUID orderId;
  private UUID customerId;
  private UUID restaurantId;
  private OrderStatus orderStatus;
//  private BigDecimal price;
//  private String street;
//  private String city;
//  private String postalCode;
//
//  @AggregateMember
//  private List<OrderAggregateItem> items;

  @CommandHandler
  public OrderAggregate(CreateOrderCommand command) {
    OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
        .orderId(command.getOrderId())
        .customerId(command.getCustomerId())
        .restaurantId(command.getRestaurantId())
        .price(command.getPrice())
        .city(command.getCity())
        .street(command.getStreet())
        .postalCode(command.getPostalCode())
        .items(command.getItems())
        .build();

    AggregateLifecycle.apply(orderCreatedEvent);
  }

  @EventSourcingHandler
  public void handle(OrderCreatedEvent event, OrderService orderService) {
    this.orderId = event.getOrderId();
    this.customerId = event.getCustomerId();
    this.restaurantId = event.getRestaurantId();
    this.orderStatus = OrderStatus.PENDING;

    orderService.createOrder(event);
  }

}
