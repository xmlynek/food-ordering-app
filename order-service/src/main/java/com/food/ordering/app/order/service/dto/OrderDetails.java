package com.food.ordering.app.order.service.dto;

import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.order.service.entity.OrderStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderDetails(UUID id,
                           UUID restaurantId,
                           String restaurantName,
                           LocalDateTime createdAt,
                           LocalDateTime lastModifiedAt,
                           OrderStatus orderStatus,
                           KitchenTicketStatus kitchenTicketStatus,
                           DeliveryStatus deliveryStatus,
                           BigDecimal totalPrice,
                           String failureMessage,
                           List<OrderItemDetails> items) implements Serializable {

}
