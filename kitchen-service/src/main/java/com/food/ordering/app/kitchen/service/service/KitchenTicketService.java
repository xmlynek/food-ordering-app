package com.food.ordering.app.kitchen.service.service;

import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import com.food.ordering.app.kitchen.service.repository.projection.KitchenTicketDetailsView;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface KitchenTicketService {

  KitchenTicket createKitchenTicket(CreateKitchenTicketCommand createKitchenTicketCommand);

  Page<KitchenTicket> getAllKitchenTicketsByRestaurantId(UUID restaurantId, Pageable pageable);

  KitchenTicketDetailsView getKitchenTicketDetails(UUID restaurantId, UUID ticketId);

  void completeKitchenTicket(UUID restaurantId, UUID ticketId);
}