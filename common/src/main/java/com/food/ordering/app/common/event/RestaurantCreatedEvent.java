package com.food.ordering.app.common.event;

import java.time.LocalDateTime;
import lombok.Builder;


@Builder
public record RestaurantCreatedEvent(String name,
                                     LocalDateTime createdAt,
                                     Boolean isAvailable,
                                     LocalDateTime lastModifiedAt) implements RestaurantDomainEvent {

}
