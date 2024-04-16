package com.food.ordering.app.order.service.aggregate;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.command.EntityId;

@RequiredArgsConstructor
public class OrderAggregateItem {

  @EntityId
  private final UUID itemId;


}
