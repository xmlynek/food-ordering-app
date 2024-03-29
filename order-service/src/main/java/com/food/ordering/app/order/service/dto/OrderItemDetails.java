package com.food.ordering.app.order.service.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderItemDetails(UUID productId,
                               String name,
                               String description,
                               Integer quantity,
                               BigDecimal price,
                               String imageUrl) {

}
