package com.food.ordering.app.kitchen.service.service;

import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import com.food.ordering.app.kitchen.service.repository.projection.KitchenTicketDetailsView;
import java.util.List;
import java.util.UUID;

public interface KitchenTicketService {

  KitchenTicket createKitchenTicket(CreateKitchenTicketCommand createKitchenTicketCommand);

  List<KitchenTicket> getAllKitchenTicketsByRestaurantId(UUID restaurantId);

  KitchenTicketDetailsView getKitchenTicketDetails(UUID restaurantId, UUID ticketId);
}
