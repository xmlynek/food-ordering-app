package com.food.ordering.app.restaurant.service.command.handler;


import com.food.ordering.app.common.command.ApproveOrderCommand;
import com.food.ordering.app.common.response.approve.OrderApproveFailed;
import com.food.ordering.app.common.response.approve.OrderApproveSucceeded;
import com.food.ordering.app.restaurant.service.entity.RestaurantOrderTicket;
import com.food.ordering.app.restaurant.service.service.RestaurantOrderTickerService;
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

  private final RestaurantOrderTickerService restaurantOrderTickerService;

  @Override
  public Message approveOrder(CommandMessage<ApproveOrderCommand> cm) {
    ApproveOrderCommand command = cm.getCommand();
    log.info("Approve order started for order {} and restaurant {}", command.orderId(),
        command.restaurantId());
    try {
      RestaurantOrderTicket orderTicket = restaurantOrderTickerService.createOrderTicket(command);
      log.info("Approve order succeeded for order {} and restaurant {}", orderTicket.getOrderId(),
          orderTicket.getRestaurant().getId());

      return CommandHandlerReplyBuilder.withSuccess(
          new OrderApproveSucceeded(command.restaurantId(), command.orderId()));
    } catch (Exception e) {
      log.error("Approve order failed for order {} and restaurant {}, :{}", command.orderId(),
          command.restaurantId(), e.getMessage(), e);
      return CommandHandlerReplyBuilder.withFailure(new OrderApproveFailed(command.restaurantId(),
          command.orderId(), e.getMessage()));
    }
  }
}
