package com.food.ordering.app.kitchen.service.command.handler;


import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.common.response.kitchen.CreateKitchenTicketFailed;
import com.food.ordering.app.common.response.kitchen.KitchenTicketCreated;
import com.food.ordering.app.kitchen.service.config.properties.SagaCommandHandlerProperties;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import com.food.ordering.app.kitchen.service.service.KitchenTicketService;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KitchenServiceCommandHandlerImpl extends KitchenServiceCommandHandler {

  private final KitchenTicketService kitchenTicketService;

  public KitchenServiceCommandHandlerImpl(SagaCommandHandlerProperties sagaCommandHandlerProperties,
      KitchenTicketService kitchenTicketService) {
    super(sagaCommandHandlerProperties);
    this.kitchenTicketService = kitchenTicketService;
  }

  @Override
  public Message createTicket(CommandMessage<CreateKitchenTicketCommand> cm) {
    CreateKitchenTicketCommand command = cm.getCommand();
    log.info("Create kitchen ticket started for order {} and restaurant {}", command.orderId(),
        command.restaurantId());
    try {
      KitchenTicket orderTicket = kitchenTicketService.createKitchenTicket(command);
      log.info("Kitchen ticket created for order {} and restaurant {}", orderTicket.getId(),
          orderTicket.getRestaurant().getId());

      return CommandHandlerReplyBuilder.withSuccess(new KitchenTicketCreated(orderTicket.getId()));
    } catch (Exception e) {
      log.error("Kitchen ticket creation failed for order {} and restaurant {}, :{}",
          command.orderId(), command.restaurantId(), e.getMessage(), e);
      return CommandHandlerReplyBuilder.withFailure(
          new CreateKitchenTicketFailed(command.orderId(), e.getMessage()));
    }
  }
}
