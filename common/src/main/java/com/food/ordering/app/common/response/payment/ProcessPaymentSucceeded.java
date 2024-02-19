package com.food.ordering.app.common.response.payment;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ProcessPaymentSucceeded(UUID paymentId, UUID orderId) implements
    ProcessPaymentResponse {

}
