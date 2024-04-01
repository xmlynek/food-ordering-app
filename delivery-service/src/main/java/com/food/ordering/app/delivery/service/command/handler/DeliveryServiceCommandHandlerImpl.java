package com.food.ordering.app.delivery.service.command.handler;


import com.food.ordering.app.common.command.PrepareOrderDeliveryCommand;
import com.food.ordering.app.delivery.service.config.properties.CommandHandlerProperties;
import com.food.ordering.app.delivery.service.entity.Delivery;
import com.food.ordering.app.delivery.service.service.DeliveryService;
import io.eventuate.tram.commands.consumer.CommandMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeliveryServiceCommandHandlerImpl extends DeliveryServiceCommandHandler {

  private final DeliveryService deliveryService;

  public DeliveryServiceCommandHandlerImpl(CommandHandlerProperties commandHandlerProperties,
      DeliveryService deliveryService) {
    super(commandHandlerProperties);
    this.deliveryService = deliveryService;
  }

  @Override
  public void prepareOrderDelivery(CommandMessage<PrepareOrderDeliveryCommand> cm) {
    PrepareOrderDeliveryCommand command = cm.getCommand();

    log.info("Prepare order delivery started for order {} in restaurant {}", command.orderId(),
        command.restaurantId());
    try {
      Delivery delivery = deliveryService.createDelivery(command);

      log.info("Order delivery initialized with id {} for order {} in restaurant {}",
          delivery.getId(), command.orderId(), command.restaurantId());

    } catch (Exception e) {
      log.error("Order delivery creation FAILED for order {} in restaurant {}, :{}",
          command.orderId(), command.restaurantId(), e.getMessage(), e);
    }
  }
}
