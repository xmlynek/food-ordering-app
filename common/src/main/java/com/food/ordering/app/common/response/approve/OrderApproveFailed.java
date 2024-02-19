package com.food.ordering.app.common.response.approve;

import java.util.UUID;

public record OrderApproveFailed(UUID restaurantId, UUID orderId, String failureMessage) implements
    ApproveOrderResponse {

}
