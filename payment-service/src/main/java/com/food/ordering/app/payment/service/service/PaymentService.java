package com.food.ordering.app.payment.service.service;

import com.food.ordering.app.common.enums.PaymentStatus;
import com.food.ordering.app.payment.service.entity.Payment;
import java.util.UUID;

public interface PaymentService {

  Payment savePayment(Payment payment);

  Payment getPaymentById(UUID paymentId);

  void updateStatus(UUID paymentId, PaymentStatus paymentStatus);

}
