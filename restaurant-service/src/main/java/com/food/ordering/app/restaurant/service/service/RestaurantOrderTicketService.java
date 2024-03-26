package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.common.command.ApproveOrderCommand;
import com.food.ordering.app.restaurant.service.entity.RestaurantOrderTicket;
import java.util.List;
import java.util.UUID;

public interface RestaurantOrderTicketService {

  RestaurantOrderTicket createOrderTicket(ApproveOrderCommand approveOrderCommand);

  List<RestaurantOrderTicket> getAllOrderTicketsByRestaurantId(UUID restaurantId);

  RestaurantOrderTicket getOrderTicketByRestaurantIdAndOrderId(UUID restaurantId, UUID orderId);
}
