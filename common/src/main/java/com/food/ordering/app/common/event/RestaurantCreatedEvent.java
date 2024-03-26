package com.food.ordering.app.common.event;

import com.food.ordering.app.common.model.Address;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;


@Builder
public record RestaurantCreatedEvent(String name,
                                     String description,
                                     UUID ownerId,
                                     Address address,
                                     LocalDateTime createdAt,
                                     Boolean isAvailable,
                                     LocalDateTime lastModifiedAt) implements
    RestaurantDomainEvent {

}
