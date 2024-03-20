package com.food.ordering.app.common.event;

import com.food.ordering.app.common.model.Address;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record RestaurantRevisedEvent(String name,
                                     String description,
                                     Address address,
                                     Boolean isAvailable,
                                     LocalDateTime lastModifiedAt) implements
    RestaurantDomainEvent {

}
