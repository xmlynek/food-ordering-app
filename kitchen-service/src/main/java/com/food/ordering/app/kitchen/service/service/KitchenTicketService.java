package com.food.ordering.app.kitchen.service.service;

import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import java.util.List;
import java.util.UUID;

public interface KitchenTicketService {

  KitchenTicket createOrderTicket(CreateKitchenTicketCommand createKitchenTicketCommand);

  List<KitchenTicket> getAllOrderTicketsByRestaurantId(UUID restaurantId);

  KitchenTicket getOrderTicketByRestaurantIdAndOrderId(UUID restaurantId, UUID orderId);
}
