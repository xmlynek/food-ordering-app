package com.food.ordering.app.common.model;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderProduct(UUID productId, Integer quantity, BigDecimal price) {

}
