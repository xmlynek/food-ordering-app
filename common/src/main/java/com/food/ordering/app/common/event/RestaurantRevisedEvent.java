package com.food.ordering.app.common.event;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record RestaurantRevisedEvent(String name,
                                     Boolean isAvailable,
                                     LocalDateTime lastModifiedAt) implements RestaurantDomainEvent {

}
