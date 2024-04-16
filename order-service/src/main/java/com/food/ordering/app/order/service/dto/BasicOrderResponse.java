package com.food.ordering.app.order.service.dto;

import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.order.service.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BasicOrderResponse(UUID id,
                                 UUID restaurantId,
                                 LocalDateTime createdAt,
                                 OrderStatus orderStatus,
                                 KitchenTicketStatus kitchenTicketStatus,
                                 DeliveryStatus deliveryStatus,
                                 String failureMessage,
                                 BigDecimal totalPrice) {

}
