package com.food.ordering.app.delivery.service.service;

import com.food.ordering.app.common.command.PrepareOrderDeliveryCommand;
import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import com.food.ordering.app.delivery.service.entity.Delivery;
import com.food.ordering.app.delivery.service.entity.Restaurant;
import com.food.ordering.app.delivery.service.exception.DeliveryNotFoundException;
import com.food.ordering.app.delivery.service.mapper.DeliveryMapper;
import com.food.ordering.app.delivery.service.repository.DeliveryRepository;
import com.food.ordering.app.delivery.service.repository.RestaurantRepository;
import com.food.ordering.app.delivery.service.repository.projection.DeliveryDetailsView;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final RestaurantRepository restaurantRepository;
  private final DeliveryMapper deliveryMapper;


  @Override
  public Delivery createDelivery(PrepareOrderDeliveryCommand command) {
    Restaurant restaurant = restaurantRepository.findById(command.restaurantId())
        .orElseThrow(() -> new RestaurantNotFoundException(command.restaurantId()));

    Delivery delivery = deliveryMapper.prepareOrderDeliveryCommandToDelivery(command);

    delivery.setDeliveryStatus(DeliveryStatus.WAITING_FOR_KITCHEN);
    delivery.setRestaurant(restaurant);

    return deliveryRepository.save(delivery);
  }

  @Override
  public Page<DeliveryDetailsView> getAllDeliveryDetailsViews(Pageable pageable) {
    return deliveryRepository.findAllBy(pageable, DeliveryDetailsView.class);
  }

  @Override
  public DeliveryDetailsView getDeliveryDetailsViewById(UUID deliveryId) {
    return deliveryRepository.findById(deliveryId, DeliveryDetailsView.class)
        .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));
  }

  @Override
  public DeliveryDetailsView updateDeliveryDetailsViewById(UUID deliveryId,
      DeliveryStatus deliveryStatus) {
    return null;
  }
}
