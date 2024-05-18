package com.food.ordering.app.kitchen.service.service;

import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.response.kitchen.KitchenTicketCreated;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import com.food.ordering.app.kitchen.service.repository.projection.KitchenTicketDetailsView;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface KitchenTicketService {

  KitchenTicketCreated createKitchenTicket(CreateKitchenTicketCommand createKitchenTicketCommand);

  void cancelKitchenTicket(UUID ticketId);

  Page<KitchenTicket> getAllKitchenTicketsByRestaurantId(UUID restaurantId, Pageable pageable, KitchenTicketStatus ticketStatus);

  KitchenTicketDetailsView getKitchenTicketDetails(UUID restaurantId, UUID ticketId);

  void completeKitchenTicket(UUID restaurantId, UUID ticketId);

  void updateDeliveryDetails(UUID ticketId, UUID deliveryId, DeliveryStatus deliveryStatus);

}
