package com.food.ordering.app.order.service.saga.proxy;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.common.model.OrderProduct;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class KitchenServiceProxy {

  public CommandWithDestination createKitchenTicket(UUID orderId, UUID customerId, UUID restaurantId,
      List<OrderProduct> products) {
    return send(new CreateKitchenTicketCommand(orderId, customerId, restaurantId, products))
        // TODO: add to config
        .to("kitchen-service").build();
  }
}
