package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.common.command.ApproveOrderCommand;
import com.food.ordering.app.common.enums.RestaurantOrderTicketStatus;
import com.food.ordering.app.common.exception.InvalidPriceValueException;
import com.food.ordering.app.common.exception.InvalidQuantityValueException;
import com.food.ordering.app.common.model.OrderProduct;
import com.food.ordering.app.common.utils.ValidateOrderUtils;
import com.food.ordering.app.restaurant.service.entity.MenuItem;
import com.food.ordering.app.restaurant.service.entity.OrderTicketItem;
import com.food.ordering.app.restaurant.service.entity.Restaurant;
import com.food.ordering.app.restaurant.service.entity.RestaurantOrderTicket;
import com.food.ordering.app.restaurant.service.exception.MenuItemNotAvailableException;
import com.food.ordering.app.restaurant.service.exception.OrderTicketNotFoundException;
import com.food.ordering.app.restaurant.service.repository.OrderTicketRepository;
import io.eventuate.examples.common.money.Money;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantOrderTickerServiceImpl implements RestaurantOrderTickerService {

  private final RestaurantMenuItemService restaurantMenuItemService;
  private final RestaurantService restaurantService;
  private final OrderTicketRepository orderTicketRepository;

  @Override
  public List<RestaurantOrderTicket> getAllOrderTicketsByRestaurantId(UUID restaurantId) {
    return orderTicketRepository.findAllByRestaurantId(restaurantId);
  }

  @Override
  public RestaurantOrderTicket getOrderTicketByRestaurantIdAndOrderId(UUID restaurantId,
      UUID orderId) {
    return orderTicketRepository.findByRestaurantIdAndId(restaurantId, orderId)
        .orElseThrow(() -> new OrderTicketNotFoundException(restaurantId, orderId));
  }

  /**
   * Creates a new order ticket based on the given command.
   *
   * @param command the command containing the order details
   * @return the newly created order ticket
   * @throws MenuItemNotAvailableException if a menu item is not available or has been deleted
   * @throws InvalidQuantityValueException if the quantity is less than or equal to zero
   * @throws InvalidPriceValueException    if the price is less than or equal to zero
   */
  @Override
  @Transactional
  public RestaurantOrderTicket createOrderTicket(ApproveOrderCommand command) {
    Restaurant restaurant = restaurantService.getRestaurantById(command.restaurantId());

    RestaurantOrderTicket orderTicket = RestaurantOrderTicket.builder()
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

    return orderTicketRepository.save(orderTicket);
  }

  /**
   * Creates a list of OrderTicketItem objects from a list of OrderProduct objects.
   *
   * @param products     a list of OrderProduct objects
   * @param restaurantId the ID of the restaurant
   * @param orderTicket  the order ticket object
   * @return a list of OrderTicketItem objects
   * @throws MenuItemNotAvailableException if a menu item is not available or has been deleted
   * @throws InvalidQuantityValueException if the quantity is less than or equal to zero
   * @throws InvalidPriceValueException    if the price is less than or equal to zero
   */
  private List<OrderTicketItem> createOrderTicketItems(List<OrderProduct> products,
      UUID restaurantId, RestaurantOrderTicket orderTicket) {
    return products.stream().map(product -> {
      MenuItem menuItemById = restaurantMenuItemService.getMenuItemById(restaurantId,
          product.productId());

      validateOrderItem(product, menuItemById);

      return OrderTicketItem.builder()
          .menuItem(menuItemById)
          .price(product.price().getAmount())
          .quantity(product.quantity())
          .orderTicket(orderTicket)
          .build();
    }).collect(Collectors.toList());
  }

  /**
   * Validates an order item.
   *
   * @param product  the order product
   * @param menuItem the menu item
   * @throws InvalidQuantityValueException if the quantity is less than or equal to zero
   * @throws InvalidPriceValueException    if the price is less than or equal to zero
   * @throws MenuItemNotAvailableException if the menu item is not available or has been deleted
   */
  private void validateOrderItem(OrderProduct product, MenuItem menuItem) {
    ValidateOrderUtils.validateOrderProduct(product);

    // check availability
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
