package com.food.ordering.app.event;


import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class OrderCreatedEvent {

  private UUID orderId;

}
