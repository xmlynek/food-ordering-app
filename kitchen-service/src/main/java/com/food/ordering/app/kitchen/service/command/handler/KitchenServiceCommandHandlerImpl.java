package com.food.ordering.app.kitchen.service.command.handler;


import com.food.ordering.app.common.command.CancelKitchenTicketCommand;
import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.common.response.kitchen.CreateKitchenTicketFailed;
import com.food.ordering.app.common.response.kitchen.KitchenTicketCreated;
import com.food.ordering.app.kitchen.service.config.properties.SagaCommandHandlerProperties;
import com.food.ordering.app.kitchen.service.service.KitchenTicketService;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KitchenServiceCommandHandlerImpl extends KitchenServiceCommandHandler {

  public static final String CREATE_KITCHEN_TICKET_FAILED_MESSAGE = "Could not create kitchen ticket.";

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
      KitchenTicketCreated kitchenTicketCreated = kitchenTicketService.createKitchenTicket(command);
      log.info("Kitchen ticket created with ticket id: {} for order {} and restaurant {}",
          kitchenTicketCreated.ticketId(), command.orderId(), command.restaurantId());

      return CommandHandlerReplyBuilder.withSuccess(kitchenTicketCreated);
    } catch (Exception e) {
      log.error("Kitchen ticket creation failed for order {} and restaurant {}, :{}",
          command.orderId(), command.restaurantId(), e.getMessage(), e);
      return CommandHandlerReplyBuilder.withFailure(
          new CreateKitchenTicketFailed(command.orderId(), CREATE_KITCHEN_TICKET_FAILED_MESSAGE));
    }
  }

  @Override
  protected Message cancelTicket(CommandMessage<CancelKitchenTicketCommand> cm) {
    CancelKitchenTicketCommand command = cm.getCommand();
    log.info("Cancel kitchen ticket started for ticket id: {}", command.ticketId());
    try {
      kitchenTicketService.cancelKitchenTicket(command.ticketId());
      log.info("Kitchen ticket cancelled with ticket id: {}", command.ticketId());

      return CommandHandlerReplyBuilder.withSuccess();
    } catch (Exception e) {
      log.error("Kitchen ticket cancellation failed for ticket id: {}, :{}", command.ticketId(),
          e.getMessage(), e);
      return CommandHandlerReplyBuilder.withFailure();
    }
  }
}
