package com.food.ordering.app.common.response.payment;

import com.food.ordering.app.common.enums.PaymentStatus;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ProcessPaymentFailed(UUID orderId, UUID customerId, PaymentStatus paymentStatus,
                                   String failureMessage) implements ProcessPaymentResponse {

}
