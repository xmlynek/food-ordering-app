package com.food.ordering.app.order.service.saga.proxy;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import com.food.ordering.app.common.command.CancelKitchenTicketCommand;
import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.common.model.OrderProduct;
import com.food.ordering.app.order.service.config.properties.CommandDestinationProperties;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KitchenServiceProxy {

  private final CommandDestinationProperties commandDestinationProperties;

  public CommandWithDestination createKitchenTicket(UUID orderId, UUID customerId,
      UUID restaurantId, List<OrderProduct> products) {
    return send(new CreateKitchenTicketCommand(orderId, customerId, restaurantId, products))
        .to(commandDestinationProperties.getKitchenService())
        .build();
  }

  public CommandWithDestination cancelKitchenTicket(UUID ticketId) {
    return send(new CancelKitchenTicketCommand(ticketId))
        .to(commandDestinationProperties.getKitchenService())
        .build();
  }
}
