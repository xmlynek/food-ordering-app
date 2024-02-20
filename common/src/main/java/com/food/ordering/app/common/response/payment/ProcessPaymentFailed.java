package com.food.ordering.app.common.response.payment;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ProcessPaymentFailed(UUID orderId, UUID customerId, String failureMessage) implements
    ProcessPaymentResponse {

}
