package com.food.ordering.app.common.response.delivery;

import com.food.ordering.app.common.enums.DeliveryStatus;
import java.util.UUID;

public record DeliveryOrderCreated(UUID deliveryId, DeliveryStatus deliveryStatus) implements
    CreateDeliveryOrderResponse {

}
