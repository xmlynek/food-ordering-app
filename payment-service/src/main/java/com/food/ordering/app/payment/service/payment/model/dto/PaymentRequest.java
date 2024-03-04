package com.food.ordering.app.payment.service.payment.model.dto;

import com.food.ordering.app.payment.service.payment.model.enums.Currency;
import io.eventuate.examples.common.money.Money;

public record PaymentRequest(Currency currency,
                             Money amount,
                             String description,
                             String paymentToken) {

}
