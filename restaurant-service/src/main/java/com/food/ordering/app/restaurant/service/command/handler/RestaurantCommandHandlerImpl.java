package com.food.ordering.app.restaurant.service.command.handler;


import com.food.ordering.app.common.command.ApproveOrderCommand;
import com.food.ordering.app.common.response.approve.OrderApproveSucceeded;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantCommandHandlerImpl extends RestaurantCommandHandler {


  @Override
  protected Message approveOrder(CommandMessage<ApproveOrderCommand> cm) {
    ApproveOrderCommand command = cm.getCommand();
    log.info("Approve order started for order {} and restaurant {}", command.orderId(),
        command.restaurantId());

    return CommandHandlerReplyBuilder.withSuccess(
        new OrderApproveSucceeded(command.restaurantId(), command.orderId()));
  }
}
