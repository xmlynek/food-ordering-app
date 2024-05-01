package com.food.ordering.app.order.service.saga.proxy;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import com.food.ordering.app.common.command.CreateDeliveryOrderCommand;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.model.Address;
import com.food.ordering.app.order.service.config.properties.CommandDestinationProperties;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryServiceProxy {

  private final CommandDestinationProperties commandDestinationProperties;

  public CommandWithDestination createDeliveryOrder(UUID orderId, UUID customerId,
      UUID restaurantId, UUID kitchenTicketId, KitchenTicketStatus kitchenTicketStatus,
      Address deliveryAddress) {
    return send(new CreateDeliveryOrderCommand(orderId, customerId, restaurantId, kitchenTicketId,
        kitchenTicketStatus, deliveryAddress))
        .to(commandDestinationProperties.getDeliveryService())
        .build();
  }

}
