package com.food.ordering.app.order.service.saga.proxy;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import com.food.ordering.app.common.command.ApproveOrderCommand;
import com.food.ordering.app.common.model.OrderProduct;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceProxy {

  public CommandWithDestination approveOrder(UUID orderId, UUID customerId, UUID restaurantId, List<OrderProduct> products) {
    return send(new ApproveOrderCommand(orderId, customerId, restaurantId, products))
        // TODO: add to config
        .to("restaurant-service")
        .build();
  }

}
