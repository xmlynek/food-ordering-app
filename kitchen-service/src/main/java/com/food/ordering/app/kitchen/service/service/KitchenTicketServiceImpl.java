package com.food.ordering.app.kitchen.service.service;

import static com.food.ordering.app.kitchen.service.exception.KitchenTicketNotFoundException.KITCHEN_TICKET_WITH_DELIVERY_ID_NOT_FOUND_EXCEPTION_MESSAGE;

import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.event.KitchenTicketStatusChangedEvent;
import com.food.ordering.app.common.exception.InvalidPriceValueException;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import com.food.ordering.app.common.model.OrderProduct;
import com.food.ordering.app.common.response.kitchen.KitchenTicketCreated;
import com.food.ordering.app.common.utils.ValidateOrderUtils;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import com.food.ordering.app.kitchen.service.entity.KitchenTicketItem;
import com.food.ordering.app.kitchen.service.entity.MenuItem;
import com.food.ordering.app.kitchen.service.entity.Restaurant;
import com.food.ordering.app.kitchen.service.event.publisher.KitchenDomainEventPublisher;
import com.food.ordering.app.kitchen.service.exception.IllegalKitchenTicketStatusException;
import com.food.ordering.app.kitchen.service.exception.KitchenTicketNotFoundException;
import com.food.ordering.app.kitchen.service.exception.MenuItemNotAvailableException;
import com.food.ordering.app.kitchen.service.repository.KitchenTicketRepository;
import com.food.ordering.app.kitchen.service.repository.RestaurantMenuItemRepository;
import com.food.ordering.app.kitchen.service.repository.RestaurantRepository;
import com.food.ordering.app.kitchen.service.repository.projection.KitchenTicketDetailsView;
import com.food.ordering.app.kitchen.service.repository.specification.KitchenTicketSpecifications;
import io.eventuate.examples.common.money.Money;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KitchenTicketServiceImpl implements KitchenTicketService {

  private final RestaurantMenuItemRepository menuItemRepository;
  private final RestaurantRepository restaurantRepository;
  private final KitchenTicketRepository kitchenTicketRepository;
  private final KitchenDomainEventPublisher domainEventPublisher;

  @Override
  public Page<KitchenTicket> getAllKitchenTicketsByRestaurantId(UUID restaurantId,
      Pageable pageable, KitchenTicketStatus ticketStatus) {
    Specification<KitchenTicket> spec = KitchenTicketSpecifications
        .withRestaurantIdAndRestaurantOwnerIdAndOptionalStatus(restaurantId,
            SecurityContextHolder.getContext().getAuthentication().getName(), ticketStatus);
    return kitchenTicketRepository.findAll(spec, pageable);
  }

  @Override
  @Cacheable(value = "kitchenTicketDetails", key = "{authentication.name, #ticketId}")
  public KitchenTicketDetailsView getKitchenTicketDetails(UUID restaurantId, UUID ticketId) {
    return kitchenTicketRepository.findByIdAndRestaurantIdAndRestaurantOwnerId(ticketId,
            restaurantId, SecurityContextHolder.getContext().getAuthentication().getName(),
            KitchenTicketDetailsView.class)
        .orElseThrow(() -> new KitchenTicketNotFoundException(restaurantId, ticketId));
  }

  @Override
  @CacheEvict(value = "kitchenTicketDetails", key = "{authentication.name, #ticketId}", beforeInvocation = true)
  public void cancelKitchenTicket(UUID ticketId) {
    KitchenTicket kitchenTicket = kitchenTicketRepository.findById(ticketId)
        .orElseThrow(() -> new KitchenTicketNotFoundException(ticketId));
    kitchenTicket.setStatus(KitchenTicketStatus.CANCELLED);
    kitchenTicketRepository.save(kitchenTicket);
  }

  @Override
  @Transactional
  @CacheEvict(value = "kitchenTicketDetails", key = "{authentication.name, #ticketId}", beforeInvocation = true)
  public void completeKitchenTicket(UUID restaurantId, UUID ticketId) {
    KitchenTicket kitchenTicket = kitchenTicketRepository.findByIdAndRestaurantIdAndRestaurantOwnerId(
            ticketId, restaurantId, SecurityContextHolder.getContext().getAuthentication().getName(),
            KitchenTicket.class)
        .orElseThrow(() -> new KitchenTicketNotFoundException(restaurantId, ticketId));

    if (kitchenTicket.getStatus() != KitchenTicketStatus.PREPARING) {
      throw new IllegalKitchenTicketStatusException(kitchenTicket.getStatus().name(),
          "Complete ticket");
    }

    kitchenTicket.setStatus(KitchenTicketStatus.READY_FOR_DELIVERY);
    kitchenTicketRepository.save(kitchenTicket);

    domainEventPublisher.publish(kitchenTicket, Collections.singletonList(
        new KitchenTicketStatusChangedEvent(kitchenTicket.getId(), kitchenTicket.getStatus())));
  }

  @Override
  @CacheEvict(value = "kitchenTicketDetails", key = "{authentication.name, #ticketId}", beforeInvocation = true)
  public void updateDeliveryDetails(UUID ticketId, UUID deliveryId, DeliveryStatus deliveryStatus) {
    KitchenTicket kitchenTicket = kitchenTicketRepository.findById(ticketId)
        .orElseThrow(() -> new KitchenTicketNotFoundException(ticketId));

    if (kitchenTicket.getDeliveryId() != null && kitchenTicket.getDeliveryId() != deliveryId) {
      throw new KitchenTicketNotFoundException(
          String.format(KITCHEN_TICKET_WITH_DELIVERY_ID_NOT_FOUND_EXCEPTION_MESSAGE, ticketId,
              deliveryId));
    }

    kitchenTicket.setDeliveryId(deliveryId);
    kitchenTicket.setDeliveryStatus(deliveryStatus);

    if (deliveryStatus == DeliveryStatus.AT_DELIVERY) {
      kitchenTicket.setStatus(KitchenTicketStatus.FINISHED);

      domainEventPublisher.publish(kitchenTicket, Collections.singletonList(
          new KitchenTicketStatusChangedEvent(kitchenTicket.getId(), kitchenTicket.getStatus())));
    }
    kitchenTicketRepository.save(kitchenTicket);
  }

  @Override
  public KitchenTicketCreated createKitchenTicket(CreateKitchenTicketCommand command) {
    Restaurant restaurant = restaurantRepository.findByIdAndIsDeletedFalseAndIsAvailableTrue(
            command.restaurantId())
        .orElseThrow(() -> new RestaurantNotFoundException(command.restaurantId()));

    KitchenTicket kitchenTicket = KitchenTicket.builder().id(command.orderId())
        .customerId(command.customerId()).restaurant(restaurant)
        .status(KitchenTicketStatus.PREPARING).build();

    kitchenTicket.setTicketItems(
        createKitchenTicketItems(command.products(), command.restaurantId(), kitchenTicket));

    kitchenTicket.setTotalPrice(kitchenTicket.getTicketItems().stream()
        .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add));

    KitchenTicket createdTicket = kitchenTicketRepository.save(kitchenTicket);
    return new KitchenTicketCreated(createdTicket.getId(), createdTicket.getStatus());
  }


  private List<KitchenTicketItem> createKitchenTicketItems(List<OrderProduct> products,
      UUID restaurantId, KitchenTicket kitchenTicket) {
    return products.stream().map(product -> {
      MenuItem menuItemById = menuItemRepository.findByIdAndRestaurantIdAndIsDeletedFalseAndIsAvailableTrue(
              product.productId(), restaurantId)
          .orElseThrow(() -> new MenuItemNotAvailableException(product.productId()));

      validateOrderItem(product, menuItemById);

      return KitchenTicketItem.builder().menuItem(menuItemById).price(product.price().getAmount())
          .quantity(product.quantity()).kitchenTicket(kitchenTicket).build();
    }).collect(Collectors.toList());
  }

  private void validateOrderItem(OrderProduct product, MenuItem menuItem) {
    ValidateOrderUtils.validateOrderProduct(product);

    // double-check availability
    if (menuItem.getIsDeleted() || !menuItem.getIsAvailable()) {
      throw new MenuItemNotAvailableException(product.productId());
    }
    // check price
    if (!product.price().equals(new Money(menuItem.getPrice()))) {
      throw new InvalidPriceValueException(product.price().getAmount(), product.productId());
    }
  }

}
