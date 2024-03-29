package com.food.ordering.app.order.service.service;

import com.food.ordering.app.common.utils.ValidateOrderUtils;
import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.entity.OrderItem;
import com.food.ordering.app.order.service.exception.InvalidOrderRequestDataException;
import com.food.ordering.app.order.service.mapper.OrderMapper;
import com.food.ordering.app.order.service.repository.RestaurantMenuItemRepository;
import com.food.ordering.app.order.service.repository.RestaurantRepository;
import com.food.ordering.app.order.service.saga.CreateOrderSaga;
import com.food.ordering.app.order.service.saga.data.CreateOrderSagaData;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderSagaServiceImpl implements OrderSagaService {

  private final RestaurantMenuItemRepository menuItemRepository;
  private final RestaurantRepository restaurantRepository;
  private final OrderService orderService;
  private final SagaInstanceFactory sagaInstanceFactory;
  private final CreateOrderSaga createOrderSaga;
  private final OrderMapper orderMapper;


  @Override
  @Transactional
  public Order saveOrderAndCreateOrderSaga(Order order) {
    Order savedOrder = orderService.createOrder(order);
    log.info("Creating order {}", savedOrder.getId().toString());

    CreateOrderSagaData orderSagaData = orderMapper.orderEntityToCreateOrderSagaData(savedOrder);

    log.info("Validating order {}", savedOrder.getId().toString());
    validateOrderCorrectness(order);
    ValidateOrderUtils.validateOrderProducts(orderSagaData.getItems());
    ValidateOrderUtils.validateTotalPriceEqualsToSumOfProductPrices(orderSagaData.getItems(),
        orderSagaData.getTotalPrice());
    log.info("Order {} is valid", savedOrder.getId().toString());

    sagaInstanceFactory.create(createOrderSaga, orderSagaData);
    return savedOrder;
  }

  private void validateOrderCorrectness(Order order) {
    if (!restaurantRepository.existsByIdAndIsDeletedFalseAndIsAvailableTrue(
        order.getRestaurantId())) {
      throw new InvalidOrderRequestDataException(
          String.format("Restaurant with id %s does not exist or is not available",
              order.getRestaurantId().toString()));
    }

    List<UUID> invalidItemIds = order.getItems().stream()
        .filter(
            orderItem -> !menuItemRepository.existsByIdAndRestaurantIdAndIsDeletedFalseAndIsAvailableTrueAndPriceEquals(
                orderItem.getProductId(), order.getRestaurantId(), orderItem.getPrice()))
        .map(OrderItem::getProductId)
        .toList();

    if (!invalidItemIds.isEmpty()) {
      String invalidItemIdsString = invalidItemIds.stream()
          .map(UUID::toString)
          .collect(Collectors.joining(", "));

      throw new InvalidOrderRequestDataException(
          String.format(
              "Some of the products do not exist, are not available, or the price does not match for the following product IDs: %s",
              invalidItemIdsString));
    }
  }

}
