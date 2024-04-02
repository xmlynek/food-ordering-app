package com.food.ordering.app.delivery.service.service;

import com.food.ordering.app.common.command.PrepareOrderDeliveryCommand;
import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.event.DeliveryAssignedToCourierEvent;
import com.food.ordering.app.common.event.DeliveryStatusChangedEvent;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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

    UUID courierId = UUID.fromString(
        SecurityContextHolder.getContext().getAuthentication().getName());

    log.info("Assigning delivery {} to courier {}", deliveryId, courierId);

    if (delivery.getCourierId() != null) {
      log.error("Delivery {} is already assigned to courier {}", deliveryId,
          delivery.getCourierId());
      throw new DeliveryAlreadyAssignedException(delivery.getCourierId());
    }

    if (delivery.getKitchenTicketStatus() != KitchenTicketStatus.READY_FOR_DELIVERY
        && delivery.getKitchenTicketStatus() != KitchenTicketStatus.PREPARING) {
      log.error("Delivery {} has illegal kitchen ticket status {}", deliveryId,
          delivery.getKitchenTicketStatus());
      throw new IllegalKitchenTicketStatusException(delivery.getKitchenTicketStatus().name(),
          "assign delivery to courier");
    }

    if (delivery.getDeliveryStatus() != DeliveryStatus.WAITING_FOR_KITCHEN) {
      log.error("Delivery {} has illegal delivery status {}", deliveryId,
          delivery.getDeliveryStatus());
      throw new IllegalDeliveryStatusException(delivery.getDeliveryStatus().name(),
          "assign delivery to courier");
    }

    delivery.setLastModifiedAt(LocalDateTime.now());

    delivery.setCourierId(
        courierId);

    DeliveryAssignedToCourierEvent deliveryAssignedToCourierEvent = deliveryMapper.deliveryToDeliveryAssignedToCourierEvent(
        delivery);

    Delivery updatedDelivery = deliveryRepository.save(delivery);

    deliveryDomainEventPublisher.publish(updatedDelivery,
        Collections.singletonList(deliveryAssignedToCourierEvent));

    log.info("Delivery {} assigned to courier {}", deliveryId, courierId);
  }

  @Override
  @Transactional
  public void pickUpDelivery(UUID deliveryId) {
    UUID courierId = UUID.fromString(
        SecurityContextHolder.getContext().getAuthentication().getName());
    log.info("Courier {} picking up delivery {}", courierId, deliveryId);

    Delivery delivery = deliveryRepository.findByIdAndCourierId(deliveryId,
            courierId)
        .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));

    if (delivery.getKitchenTicketStatus() != KitchenTicketStatus.READY_FOR_DELIVERY) {
      log.error("Delivery {} has illegal kitchen ticket status {}", deliveryId,
          delivery.getKitchenTicketStatus());
      throw new IllegalKitchenTicketStatusException(delivery.getKitchenTicketStatus().name(),
          "pick up delivery");
    }

    if (delivery.getDeliveryStatus() != DeliveryStatus.WAITING_FOR_KITCHEN) {
      log.error("Delivery {} has illegal delivery status {}", deliveryId,
          delivery.getDeliveryStatus());
      throw new IllegalDeliveryStatusException(delivery.getDeliveryStatus().name(),
          "pick up delivery");
    }

    delivery.setLastModifiedAt(LocalDateTime.now());
    delivery.setDeliveryStatus(DeliveryStatus.AT_DELIVERY);
    DeliveryStatusChangedEvent deliveryStatusChangedEvent = deliveryMapper.deliveryToDeliveryStatusChangedEvent(
        delivery);

    Delivery updatedDelivery = deliveryRepository.save(delivery);

    deliveryDomainEventPublisher.publish(updatedDelivery,
        Collections.singletonList(deliveryStatusChangedEvent));

    log.info("Courier {} picked up delivery {}", courierId, deliveryId);
  }

  @Override
  @Transactional
  public void completeDelivery(UUID deliveryId) {
    UUID courierId = UUID.fromString(
        SecurityContextHolder.getContext().getAuthentication().getName());

    log.info("Courier {} completing delivery {}", courierId, deliveryId);

    Delivery delivery = deliveryRepository.findByIdAndCourierId(deliveryId,
            courierId)
        .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));

    if (delivery.getDeliveryStatus() != DeliveryStatus.AT_DELIVERY) {
      throw new IllegalDeliveryStatusException(delivery.getDeliveryStatus().name(),
          "complete delivery");
    }

    delivery.setLastModifiedAt(LocalDateTime.now());
    delivery.setDeliveryStatus(DeliveryStatus.DELIVERED);
    DeliveryStatusChangedEvent deliveryStatusChangedEvent = deliveryMapper.deliveryToDeliveryStatusChangedEvent(
        delivery);

    Delivery updatedDelivery = deliveryRepository.save(delivery);

    deliveryDomainEventPublisher.publish(updatedDelivery,
        Collections.singletonList(deliveryStatusChangedEvent));

    log.info("Courier {} completed delivery {}", courierId, deliveryId);
  }

  @Override
  public DeliveryDetailsView getDeliveryDetailsViewById(UUID deliveryId) {
    return deliveryRepository.findById(deliveryId, DeliveryDetailsView.class)
        .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));
  }

  @Override
  public void updateKitchenTicketStatus(UUID kitchenTicketId,
      KitchenTicketStatus kitchenTicketStatus) {
    Delivery delivery = deliveryRepository.findByKitchenTicketId(kitchenTicketId)
        .orElseThrow(() -> new DeliveryNotFoundException(
            String.format("Delivery with kitchen ticket id %s not found",
                kitchenTicketId)));

    delivery.setKitchenTicketStatus(kitchenTicketStatus);
    deliveryRepository.save(delivery);
  }
}
