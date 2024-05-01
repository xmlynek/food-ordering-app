package com.food.ordering.app.common.event;

import lombok.Builder;

@Builder
public record RestaurantDeletedEvent() implements RestaurantDomainEvent {

}
