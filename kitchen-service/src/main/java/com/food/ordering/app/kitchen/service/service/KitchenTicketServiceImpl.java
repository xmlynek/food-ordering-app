package com.food.ordering.app.kitchen.service.service;

import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.event.KitchenTicketStatusChangedEvent;
import com.food.ordering.app.common.exception.InvalidPriceValueException;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import com.food.ordering.app.common.model.OrderProduct;
import com.food.ordering.app.common.utils.ValidateOrderUtils;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import com.food.ordering.app.kitchen.service.entity.KitchenTicketItem;
import com.food.ordering.app.kitchen.service.entity.MenuItem;
import com.food.ordering.app.kitchen.service.entity.Restaurant;
import com.food.ordering.app.kitchen.service.event.publisher.KitchenDomainEventPublisher;
import com.food.ordering.app.kitchen.service.exception.KitchenTicketNotFoundException;
import com.food.ordering.app.kitchen.service.exception.MenuItemNotAvailableException;
import com.food.ordering.app.kitchen.service.repository.KitchenTicketRepository;
import com.food.ordering.app.kitchen.service.repository.RestaurantMenuItemRepository;
import com.food.ordering.app.kitchen.service.repository.RestaurantRepository;
import com.food.ordering.app.kitchen.service.repository.projection.KitchenTicketDetailsView;
import io.eventuate.examples.common.money.Money;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
      Pageable pageable) {
    return kitchenTicketRepository.findAllByRestaurantIdAndRestaurantOwnerId(restaurantId,
        SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
  }

  @Override
  public KitchenTicketDetailsView getKitchenTicketDetails(UUID restaurantId, UUID ticketId) {
    return kitchenTicketRepository.findByIdAndRestaurantIdAndRestaurantOwnerId(ticketId,
            restaurantId, SecurityContextHolder.getContext().getAuthentication().getName(),
            KitchenTicketDetailsView.class)
        .orElseThrow(() -> new KitchenTicketNotFoundException(restaurantId, ticketId));
  }

  @Override
  @Transactional
  public void completeKitchenTicket(UUID restaurantId, UUID ticketId) {
    KitchenTicket kitchenTicket = kitchenTicketRepository.findByIdAndRestaurantIdAndRestaurantOwnerId(
            ticketId, restaurantId, SecurityContextHolder.getContext().getAuthentication().getName(),
            KitchenTicket.class)
        .orElseThrow(() -> new KitchenTicketNotFoundException(restaurantId, ticketId));
    kitchenTicket.setStatus(KitchenTicketStatus.READY_FOR_DELIVERY);
    kitchenTicketRepository.save(kitchenTicket);

    domainEventPublisher.publish(kitchenTicket, Collections.singletonList(
        new KitchenTicketStatusChangedEvent(kitchenTicket.getId(), kitchenTicket.getStatus())));
  }

  @Override
  public KitchenTicket createKitchenTicket(CreateKitchenTicketCommand command) {
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

    return kitchenTicketRepository.save(kitchenTicket);
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
