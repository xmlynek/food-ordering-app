package com.food.ordering.app.order.service.saga.proxy;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import com.food.ordering.app.common.command.PrepareOrderDeliveryCommand;
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

  public CommandWithDestination notifyDeliveryService(UUID orderId, UUID customerId,
      UUID restaurantId, Address deliveryAddress) {
    return send(new PrepareOrderDeliveryCommand(orderId, customerId, restaurantId, deliveryAddress))
        .to(commandDestinationProperties.getDeliveryService())
        .build();
  }

}
