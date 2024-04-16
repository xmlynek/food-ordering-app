package com.food.ordering.app.payment.service.payment.strategy;

import com.food.ordering.app.payment.service.payment.model.dto.PaymentRequest;
import com.food.ordering.app.payment.service.payment.model.dto.PaymentResult;
import com.food.ordering.app.payment.service.payment.model.dto.RefundResult;

public interface PaymentStrategy {

  PaymentResult processPayment(PaymentRequest paymentRequest) throws Exception;

  RefundResult refundPayment(String paymentId) throws Exception;
}
