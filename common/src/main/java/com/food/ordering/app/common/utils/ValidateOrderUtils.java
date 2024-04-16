package com.food.ordering.app.common.utils;

import com.food.ordering.app.common.exception.InvalidPriceValueException;
import com.food.ordering.app.common.exception.InvalidQuantityValueException;
import com.food.ordering.app.common.model.OrderProduct;
import io.eventuate.examples.common.money.Money;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ValidateOrderUtils {

  private ValidateOrderUtils() {
  }

  public static void validateOrderProducts(List<OrderProduct> orderProducts) {
    orderProducts.forEach(ValidateOrderUtils::validateOrderProduct);
  }

  public static void validateOrderProduct(OrderProduct product) {
    if (product.quantity() <= 0) {
      log.info("Invalid quantity value: {} for product id: {}", product.quantity(),
          product.productId());
      throw new InvalidQuantityValueException(product.quantity(), product.productId());
    }
    if (!product.price().isGreaterThanOrEqual(Money.ZERO)) {
      log.info("Invalid price value: {} for product id: {}",
          product.price().getAmount().toString(),
          product.productId());
      throw new InvalidPriceValueException(product.price().getAmount(),
          product.productId());
    }
  }

  public static void validateTotalPriceEqualsToSumOfProductPrices(List<OrderProduct> orderProducts,
      Money totalPrice) {
    BigDecimal sumOfProductPrices = orderProducts.stream()
        .map(item -> item.price().getAmount().multiply(new BigDecimal(item.quantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (!totalPrice.getAmount().equals(sumOfProductPrices)) {
      log.info("Invalid total price value: {}", totalPrice);
      throw new InvalidPriceValueException(totalPrice.getAmount());
    }
  }
}
