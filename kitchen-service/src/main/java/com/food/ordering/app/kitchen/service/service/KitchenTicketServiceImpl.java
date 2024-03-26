package com.food.ordering.app.kitchen.service.service;

import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.common.enums.RestaurantOrderTicketStatus;
import com.food.ordering.app.common.exception.InvalidPriceValueException;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import com.food.ordering.app.common.model.OrderProduct;
import com.food.ordering.app.common.utils.ValidateOrderUtils;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import com.food.ordering.app.kitchen.service.entity.KitchenTicketItem;
import com.food.ordering.app.kitchen.service.entity.MenuItem;
import com.food.ordering.app.kitchen.service.entity.Restaurant;
import com.food.ordering.app.kitchen.service.exception.KitchenTicketNotFoundException;
import com.food.ordering.app.kitchen.service.exception.MenuItemNotAvailableException;
import com.food.ordering.app.kitchen.service.repository.KitchenTicketRepository;
import com.food.ordering.app.kitchen.service.repository.RestaurantMenuItemRepository;
import com.food.ordering.app.kitchen.service.repository.RestaurantRepository;
import io.eventuate.examples.common.money.Money;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KitchenTicketServiceImpl implements KitchenTicketService {

  private final RestaurantMenuItemRepository menuItemRepository;
  private final RestaurantRepository restaurantRepository;
  private final KitchenTicketRepository kitchenTicketRepository;

  @Override
  public List<KitchenTicket> getAllOrderTicketsByRestaurantId(UUID restaurantId) {
    return kitchenTicketRepository.findAllByRestaurantId(restaurantId);
  }

  @Override
  public KitchenTicket getOrderTicketByRestaurantIdAndOrderId(UUID restaurantId,
      UUID orderId) {
    return kitchenTicketRepository.findByRestaurantIdAndId(restaurantId, orderId)
        .orElseThrow(() -> new KitchenTicketNotFoundException(restaurantId, orderId));
  }


  @Override
  public KitchenTicket createOrderTicket(CreateKitchenTicketCommand command) {
    Restaurant restaurant = restaurantRepository.findByIdAndIsDeletedFalseAndIsAvailableTrue(
            command.restaurantId())
        .orElseThrow(() -> new RestaurantNotFoundException(command.restaurantId()));

    KitchenTicket orderTicket = KitchenTicket.builder()
        .id(command.orderId())
        .customerId(command.customerId())
        .restaurant(restaurant)
        // TODO: set to created_PENDING?
        .status(RestaurantOrderTicketStatus.APPROVED)
        .build();

    orderTicket.setOrderItems(createOrderTicketItems(
        command.products(), command.restaurantId(), orderTicket));

    orderTicket.setTotalPrice(orderTicket.getOrderItems().stream()
        .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add));

    return kitchenTicketRepository.save(orderTicket);
  }


  private List<KitchenTicketItem> createOrderTicketItems(List<OrderProduct> products,
      UUID restaurantId, KitchenTicket orderTicket) {
    return products.stream().map(product -> {
      MenuItem menuItemById = menuItemRepository.findByIdAndRestaurantIdAndIsDeletedFalseAndIsAvailableTrue(
              product.productId(), restaurantId)
          .orElseThrow(() -> new MenuItemNotAvailableException(product.productId()));

      validateOrderItem(product, menuItemById);

      return KitchenTicketItem.builder()
          .menuItem(menuItemById)
          .price(product.price().getAmount())
          .quantity(product.quantity())
          .orderTicket(orderTicket)
          .build();
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
      throw new InvalidPriceValueException(product.price().getAmount(),
          product.productId());
    }
  }

}
