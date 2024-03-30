package com.food.ordering.app.delivery.service.service;

import com.food.ordering.app.common.command.PrepareOrderDeliveryCommand;
import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.delivery.service.entity.Delivery;
import com.food.ordering.app.delivery.service.repository.projection.DeliveryDetailsView;
import java.util.List;
import java.util.UUID;

public interface DeliveryService {

  Delivery createDelivery(PrepareOrderDeliveryCommand command);

  List<DeliveryDetailsView> getAllDeliveryDetailsViews();

  DeliveryDetailsView getDeliveryDetailsViewById(UUID deliveryId);

  DeliveryDetailsView updateDeliveryDetailsViewById(UUID deliveryId, DeliveryStatus deliveryStatus);

}
