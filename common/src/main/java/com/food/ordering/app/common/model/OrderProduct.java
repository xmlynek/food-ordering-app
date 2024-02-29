package com.food.ordering.app.common.model;

import io.eventuate.examples.common.money.Money;
import java.util.UUID;

public record OrderProduct(UUID productId, Integer quantity, Money price) {

}
