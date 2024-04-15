package com.food.ordering.app.common.response.delivery;

import java.util.UUID;

public record CreateDeliveryOrderFailed(UUID orderId, String failureMessage) implements
    CreateDeliveryOrderResponse {

}
