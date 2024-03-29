package com.food.ordering.app.order.service.dto;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.order.service.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderDto(UUID id,
                       UUID restaurantId,
                       LocalDateTime createdAt,
                       OrderStatus orderStatus,
                       KitchenTicketStatus kitchenTicketStatus,
                       String failureMessage,
                       BigDecimal totalPrice) {

}
