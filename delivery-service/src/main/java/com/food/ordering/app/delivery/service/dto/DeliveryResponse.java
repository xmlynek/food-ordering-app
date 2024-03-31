package com.food.ordering.app.delivery.service.dto;

import com.food.ordering.app.common.enums.DeliveryStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryResponse(UUID id,
                               UUID customerId,
                               UUID restaurantId,
                               UUID courierId,
                               String restaurantName,
                               DeliveryStatus deliveryStatus,
                               LocalDateTime lastModifiedAt,
                               AddressResponse deliveryAddress,
                               AddressResponse restaurantAddress) {

}
