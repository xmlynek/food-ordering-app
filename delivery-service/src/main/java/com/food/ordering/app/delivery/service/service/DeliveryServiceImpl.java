package com.food.ordering.app.delivery.service.service;

import com.food.ordering.app.common.command.PrepareOrderDeliveryCommand;
import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.event.DeliveryAssignedToCourierEvent;
import com.food.ordering.app.common.event.DeliveryPickedUpEvent;
import com.food.ordering.app.common.event.DeliveryCompletedEvent;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import com.food.ordering.app.delivery.service.entity.Delivery;
import com.food.ordering.app.delivery.service.entity.Restaurant;
import com.food.ordering.app.delivery.service.event.publisher.DeliveryDomainEventPublisher;
import com.food.ordering.app.delivery.service.exception.DeliveryAlreadyAssignedException;
import com.food.ordering.app.delivery.service.exception.DeliveryNotFoundException;
import com.food.ordering.app.delivery.service.exception.IllegalDeliveryStatusException;
import com.food.ordering.app.delivery.service.exception.IllegalKitchenTicketStatusException;
import com.food.ordering.app.delivery.service.mapper.DeliveryMapper;
import com.food.ordering.app.delivery.service.repository.DeliveryRepository;
import com.food.ordering.app.delivery.service.repository.RestaurantRepository;
import com.food.ordering.app.delivery.service.repository.projection.DeliveryDetailsView;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final RestaurantRepository restaurantRepository;
  private final DeliveryMapper deliveryMapper;
  private final DeliveryDomainEventPublisher deliveryDomainEventPublisher;


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
  @Transactional
  public void assignDeliveryToCourier(UUID deliveryId) {
    Delivery delivery = deliveryRepository.findById(deliveryId)
        .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));

    if (delivery.getCourierId() != null) {
      throw new DeliveryAlreadyAssignedException(delivery.getCourierId());
    }

    if (delivery.getKitchenTicketStatus() != KitchenTicketStatus.READY_FOR_DELIVERY
        && delivery.getKitchenTicketStatus() != KitchenTicketStatus.PREPARING) {
      throw new IllegalKitchenTicketStatusException(delivery.getKitchenTicketStatus().name(),
          "assign delivery to courier");
    }

    if (delivery.getDeliveryStatus() != DeliveryStatus.WAITING_FOR_KITCHEN) {
      throw new IllegalDeliveryStatusException(delivery.getDeliveryStatus().name(),
          "assign delivery to courier");
    }

    delivery.setLastModifiedAt(LocalDateTime.now());
    delivery.setCourierId(
        UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));

    DeliveryAssignedToCourierEvent deliveryAssignedToCourierEvent = deliveryMapper.deliveryToDeliveryAssignedToCourierEvent(
        delivery);

    Delivery updatedDelivery = deliveryRepository.save(delivery);

    deliveryDomainEventPublisher.publish(updatedDelivery,
        Collections.singletonList(deliveryAssignedToCourierEvent));
  }

  @Override
  @Transactional
  public void pickUpDelivery(UUID deliveryId) {
    Delivery delivery = deliveryRepository.findByIdAndCourierId(deliveryId,
            UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()))
        .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));

    if (delivery.getKitchenTicketStatus() != KitchenTicketStatus.READY_FOR_DELIVERY) {
      throw new IllegalKitchenTicketStatusException(delivery.getKitchenTicketStatus().name(),
          "pick up delivery");
    }

    if (delivery.getDeliveryStatus() != DeliveryStatus.WAITING_FOR_KITCHEN) {
      throw new IllegalDeliveryStatusException(delivery.getDeliveryStatus().name(),
          "pick up delivery");
    }

    delivery.setLastModifiedAt(LocalDateTime.now());
    delivery.setDeliveryStatus(DeliveryStatus.AT_DELIVERY);
    DeliveryPickedUpEvent deliveryPickedUpEvent = deliveryMapper.deliveryToDeliveryPickedUpEvent(
        delivery);

    Delivery updatedDelivery = deliveryRepository.updateDeliveryById(deliveryId, delivery);

    deliveryDomainEventPublisher.publish(updatedDelivery,
        Collections.singletonList(deliveryPickedUpEvent));
  }

  @Override
  @Transactional
  public void completeDelivery(UUID deliveryId) {
    Delivery delivery = deliveryRepository.findByIdAndCourierId(deliveryId,
            UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()))
        .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));

    if (delivery.getDeliveryStatus() != DeliveryStatus.AT_DELIVERY) {
      throw new IllegalDeliveryStatusException(delivery.getDeliveryStatus().name(),
          "complete delivery");
    }

    delivery.setLastModifiedAt(LocalDateTime.now());
    delivery.setDeliveryStatus(DeliveryStatus.DELIVERED);
    DeliveryCompletedEvent deliveryCompletedEvent = deliveryMapper.deliverySuccessfullyDeliveredEvent(
        delivery);

    Delivery updatedDelivery = deliveryRepository.updateDeliveryById(deliveryId, delivery);

    deliveryDomainEventPublisher.publish(updatedDelivery,
        Collections.singletonList(deliveryCompletedEvent));
  }

  @Override
  public DeliveryDetailsView getDeliveryDetailsViewById(UUID deliveryId) {
    return deliveryRepository.findById(deliveryId, DeliveryDetailsView.class)
        .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));
  }
}
