package com.food.ordering.app.common.response.kitchen;

import java.util.UUID;

public record CreateKitchenTicketFailed(UUID orderId, String failureMessage) implements
    CreateKitchenTicketResponse {


}
