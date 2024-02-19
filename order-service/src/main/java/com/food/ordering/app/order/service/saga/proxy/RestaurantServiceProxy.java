package com.food.ordering.app.order.service.saga.proxy;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import com.food.ordering.app.common.command.ApproveOrderCommand;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceProxy {

  public CommandWithDestination approveOrder(UUID orderId, UUID customerId, UUID restaurantId) {
    return send(new ApproveOrderCommand(orderId, customerId, restaurantId))
        // TODO: add to config
        .to("restaurant-service")
        .build();
  }

}
