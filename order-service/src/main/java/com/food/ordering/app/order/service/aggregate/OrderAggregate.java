package com.food.ordering.app.order.service.aggregate;

import com.food.ordering.app.command.CreateOrderCommand;
import com.food.ordering.app.event.OrderCreatedEvent;
import com.food.ordering.app.order.service.entity.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

  @AggregateIdentifier
  private UUID orderId;
  private UUID customerId;
  private UUID restaurantId;
  private OrderStatus orderStatus;
  private BigDecimal price;

  @AggregateMember
  private List<OrderAggregateItem> items;

  @CommandHandler
  public OrderAggregate(CreateOrderCommand command) {
    OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder().orderId(UUID.randomUUID())
        .build();

    AggregateLifecycle.apply(orderCreatedEvent);
  }

  @EventSourcingHandler
  public void handle(OrderCreatedEvent orderCreatedEvent) {
    this.orderId = UUID.randomUUID();
  }

}
