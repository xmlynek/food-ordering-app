package com.food.ordering.app.delivery.service.command.handler;


import com.food.ordering.app.common.command.CreateDeliveryOrderCommand;
import com.food.ordering.app.common.response.delivery.CreateDeliveryOrderFailed;
import com.food.ordering.app.common.response.delivery.DeliveryOrderCreated;
import com.food.ordering.app.delivery.service.config.properties.CommandHandlerProperties;
import com.food.ordering.app.delivery.service.entity.Delivery;
import com.food.ordering.app.delivery.service.service.DeliveryService;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeliveryServiceCommandHandlerImpl extends DeliveryServiceCommandHandler {

  public static final String CREATE_DELIVERY_ORDER_FAILED_MESSAGE = "Could not create delivery order.";

  private final DeliveryService deliveryService;

  public DeliveryServiceCommandHandlerImpl(CommandHandlerProperties commandHandlerProperties,
      DeliveryService deliveryService) {
    super(commandHandlerProperties);
    this.deliveryService = deliveryService;
  }

  @Override
  public Message prepareOrderDelivery(CommandMessage<CreateDeliveryOrderCommand> cm) {
    CreateDeliveryOrderCommand command = cm.getCommand();

    log.info("Prepare order delivery started for order {} in restaurant {}", command.orderId(),
        command.restaurantId());
    try {
      Delivery delivery = deliveryService.createDelivery(command);

      log.info("Order delivery initialized with id {} for order {} in restaurant {}",
          delivery.getId(), command.orderId(), command.restaurantId());

      return CommandHandlerReplyBuilder.withSuccess(
          new DeliveryOrderCreated(delivery.getId(), delivery.getDeliveryStatus()));
    } catch (Exception e) {
      log.error("Order delivery creation FAILED for order {} in restaurant {}, :{}",
          command.orderId(), command.restaurantId(), e.getMessage(), e);
      return CommandHandlerReplyBuilder.withFailure(
          new CreateDeliveryOrderFailed(command.orderId(), CREATE_DELIVERY_ORDER_FAILED_MESSAGE));
    }
  }
}
