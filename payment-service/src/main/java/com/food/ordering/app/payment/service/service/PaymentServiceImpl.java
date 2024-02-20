package com.food.ordering.app.payment.service.service;

import com.food.ordering.app.common.enums.PaymentStatus;
import com.food.ordering.app.payment.service.entity.Payment;
import com.food.ordering.app.payment.service.repository.PaymentRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Payment createPayment(Payment payment) {
    payment.setPaymentStatus(PaymentStatus.INITIALIZED);

    return paymentRepository.save(payment);
  }

  @Override
  public void updateStatus(UUID paymentId, PaymentStatus paymentStatus) {
    paymentRepository.findById(paymentId).ifPresent(p -> p.setPaymentStatus(paymentStatus));
  }
}
