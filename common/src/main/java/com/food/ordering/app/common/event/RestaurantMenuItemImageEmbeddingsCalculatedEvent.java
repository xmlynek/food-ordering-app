package com.food.ordering.app.common.event;

import lombok.Builder;

@Builder
public record RestaurantMenuItemImageEmbeddingsCalculatedEvent(String restaurantId,
                                                               String productId,
                                                               float[] embeddings) implements
    RestaurantMenuItemDomainEvent {

}
