package com.food.ordering.app.common.response.payment;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ProcessPaymentFailed(UUID paymentId, UUID orderId, String failureMessage) implements
    ProcessPaymentResponse {

}
