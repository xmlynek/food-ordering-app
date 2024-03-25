package com.food.ordering.app.order.service.dto;

import com.food.ordering.app.order.service.entity.OrderStatus;
import java.math.BigDecimal;
import java.util.UUID;

public record OrderDto(UUID id,
                       UUID restaurantId,
                       String createdAt,
                       OrderStatus orderStatus,
                       BigDecimal totalPrice) {

}
