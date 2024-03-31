package com.food.ordering.app.delivery.service.service;

import com.food.ordering.app.common.command.PrepareOrderDeliveryCommand;
import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.delivery.service.entity.Delivery;
import com.food.ordering.app.delivery.service.repository.projection.DeliveryDetailsView;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {

  Delivery createDelivery(PrepareOrderDeliveryCommand command);

  Page<DeliveryDetailsView> getAllDeliveryDetailsViews(Pageable pageable);

  DeliveryDetailsView getDeliveryDetailsViewById(UUID deliveryId);

  DeliveryDetailsView updateDeliveryDetailsViewById(UUID deliveryId, DeliveryStatus deliveryStatus);

}
