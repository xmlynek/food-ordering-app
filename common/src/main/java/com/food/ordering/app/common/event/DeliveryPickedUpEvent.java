package com.food.ordering.app.common.event;

import com.food.ordering.app.common.enums.DeliveryStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryPickedUpEvent(UUID deliveryId,
                                    UUID courierId,
                                    UUID orderId,
                                    UUID kitchenTicketId,
                                    DeliveryStatus status,
                                    LocalDateTime pickedUpAt) implements DeliveryDomainEvent {

}
