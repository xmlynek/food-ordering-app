package com.food.ordering.app.delivery.service.service;

import com.food.ordering.app.common.command.PrepareOrderDeliveryCommand;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.delivery.service.entity.Delivery;
import com.food.ordering.app.delivery.service.repository.projection.DeliveryDetailsView;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {

  Delivery createDelivery(PrepareOrderDeliveryCommand command);

  Page<DeliveryDetailsView> getAllAvailableDeliveryDetailsViews(Pageable pageable);

  Page<DeliveryDetailsView> getDeliveryHistoryForCourier(UUID courierId, Pageable pageable);

  DeliveryDetailsView getDeliveryDetailsViewById(UUID deliveryId);

  void assignDeliveryToCourier(UUID deliveryId);

  void pickUpDelivery(UUID deliveryId);

  void completeDelivery(UUID deliveryId);

  void updateKitchenTicketStatus(UUID kitchenTicketId, KitchenTicketStatus kitchenTicketStatus);
}
