package com.food.ordering.app.payment.service.service;

import com.food.ordering.app.payment.service.entity.Payment;
import com.food.ordering.app.payment.service.entity.PaymentStatus;
import com.food.ordering.app.payment.service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;

  @Override
  public Payment createPayment(Payment payment) {
    payment.setPaymentStatus(PaymentStatus.INITIALIZED);

    return paymentRepository.save(payment);
  }
}
