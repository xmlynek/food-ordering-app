package com.food.ordering.app.common.event;

import lombok.Builder;

@Builder
public record RestaurantMenuItemDeletedEvent(String restaurantId) implements RestaurantMenuItemDomainEvent {

}
