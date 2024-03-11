package com.food.ordering.app.common.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record RestaurantMenuItemRevisedEvent(String restaurantId,
                                             String name,
                                             String description,
                                             BigDecimal price,
                                             LocalDateTime lastModifiedAt,
                                             Boolean isAvailable,
                                             String imageUrl) implements RestaurantMenuItemDomainEvent {

}
