package com.food.ordering.app.order.service.service;

import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.exception.MenuItemNotFoundException;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import com.food.ordering.app.order.service.dto.OrderDetails;
import com.food.ordering.app.order.service.dto.OrderItemDetails;
import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.entity.OrderStatus;
import com.food.ordering.app.order.service.exception.OrderNotFoundException;
import com.food.ordering.app.order.service.mapper.OrderDetailsMapper;
import com.food.ordering.app.order.service.repository.OrderRepository;
import com.food.ordering.app.order.service.repository.RestaurantMenuItemRepository;
import com.food.ordering.app.order.service.repository.RestaurantRepository;
import com.food.ordering.app.order.service.repository.projection.OrderMenuItemDetailsView;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final RestaurantMenuItemRepository restaurantMenuItemRepository;
  private final RestaurantRepository restaurantRepository;
  private final OrderDetailsMapper orderDetailsMapper;

  @Override
  @Transactional
  public Order createOrder(Order order) {
    order.setOrderStatus(OrderStatus.PENDING);

    order.getItems().forEach(orderItem -> orderItem.setOrder(order));

    return orderRepository.save(order);
  }

  @Override
  @Cacheable(value = "orderDetails", key = "{@principalProviderImpl.name, #orderId}")
  public OrderDetails getOrderDetailsById(UUID orderId) {
    Order order = orderRepository.findByIdAndCustomerId(orderId,
            UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()))
        .orElseThrow(() -> new OrderNotFoundException(orderId));

    String restaurantName = restaurantRepository.findById(order.getRestaurantId())
        .orElseThrow(() -> new RestaurantNotFoundException(order.getRestaurantId()))
        .getName();

    List<OrderItemDetails> orderItemDetails = order.getItems().stream()
        .map(orderItem -> {
          OrderMenuItemDetailsView menuItemDetailsView = restaurantMenuItemRepository
              .findById(orderItem.getProductId(), OrderMenuItemDetailsView.class)
              .orElseThrow(() -> new MenuItemNotFoundException(orderItem.getProductId()));

          return orderDetailsMapper.toOrderItemDetails(orderItem, menuItemDetailsView);
        })
        .toList();

    return orderDetailsMapper.toOrderDetails(order, orderItemDetails, restaurantName);
  }

  @Override
  public Page<Order> findAllByCustomerId(UUID customerId, Pageable pageable) {
    return orderRepository.findAllByCustomerId(customerId, pageable);
  }

  @Override
  @Transactional
  @CacheEvict(value = "orderDetails", key = "{@principalProviderImpl.name, #orderId}", beforeInvocation = true)
  public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));
    order.setOrderStatus(orderStatus);
  }

  @Override
  @Transactional
  @CacheEvict(value = "orderDetails", key = "{@principalProviderImpl.name, #orderId}", beforeInvocation = true)
  public void setFailureMessages(UUID orderId, List<String> failureMessages) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));
    order.getFailureMessages().addAll(failureMessages);
  }

  @Override
  @Transactional
  // ticketId is the same as the orderId in our implementation
  @CacheEvict(value = "orderDetails", key = "{@principalProviderImpl.name, #ticketId}", beforeInvocation = true)
  public void updateKitchenTicketStatus(UUID ticketId, KitchenTicketStatus kitchenTicketStatus) {
    Order order = orderRepository.findByKitchenTicketId(ticketId)
        .orElseThrow(() -> new OrderNotFoundException(
            String.format("Order not found with kitchen ticket id %s", ticketId.toString())));
    order.setKitchenTicketStatus(kitchenTicketStatus);
  }

  @Override
  @Transactional
  @CacheEvict(value = "orderDetails", key = "{@principalProviderImpl.name, #orderId}", beforeInvocation = true)
  public void updateKitchenTicketData(UUID orderId, UUID ticketId,
      KitchenTicketStatus kitchenTicketStatus) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));
    order.setKitchenTicketId(ticketId);
    order.setKitchenTicketStatus(kitchenTicketStatus);
  }

  @Override
  @CacheEvict(value = "orderDetails", key = "{@principalProviderImpl.name, #orderId}", beforeInvocation = true)
  public void updateOrderDeliveryData(UUID orderId, UUID deliveryId,
      DeliveryStatus deliveryStatus) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));

    if (order.getDeliveryId() != null && order.getDeliveryId() != deliveryId) {
      throw new OrderNotFoundException(
          String.format("Order not found with delivery id %s", deliveryId.toString()));
    }

    order.setDeliveryId(deliveryId);
    order.setDeliveryStatus(deliveryStatus);

    if (deliveryStatus == DeliveryStatus.DELIVERED) {
      order.setOrderStatus(OrderStatus.COMPLETED);
    }

    orderRepository.save(order);
  }
}
