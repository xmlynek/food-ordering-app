package com.food.ordering.app.common.response.approve;

import java.util.UUID;

public record OrderApproveSucceeded(UUID restaurantId, UUID orderId) implements ApproveOrderResponse {

}
