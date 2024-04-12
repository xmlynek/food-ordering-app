package com.food.ordering.app.payment.service.service;

import com.food.ordering.app.common.command.CancelPaymentCommand;
import com.food.ordering.app.common.command.ProcessPaymentCommand;
import com.food.ordering.app.common.enums.PaymentStatus;
import com.food.ordering.app.payment.service.entity.Payment;
import com.food.ordering.app.payment.service.exception.PaymentNotFoundException;
import com.food.ordering.app.payment.service.mapper.PaymentMapper;
import com.food.ordering.app.payment.service.payment.model.dto.PaymentRequest;
import com.food.ordering.app.payment.service.payment.model.dto.PaymentResult;
import com.food.ordering.app.payment.service.payment.model.dto.RefundResult;
import com.food.ordering.app.payment.service.payment.model.enums.Currency;
import com.food.ordering.app.payment.service.payment.strategy.PaymentStrategy;
import com.food.ordering.app.payment.service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;
  private final PaymentStrategy paymentStrategy;
  private final PaymentMapper paymentMapper;


  @Override
  public Payment saveFailedPayment(ProcessPaymentCommand command) {
    Payment payment = paymentMapper.paymentRequestToPaymentEntity(command);
    payment.setPaymentStatus(PaymentStatus.FAILED);
    return paymentRepository.save(payment);
  }

  @Override
  public Payment processPayment(ProcessPaymentCommand command) throws Exception {
    PaymentResult paymentResult = paymentStrategy.processPayment(
        new PaymentRequest(Currency.EUR, command.amount(), "Food ordering app payment",
            command.paymentToken()));

    Payment payment = paymentMapper.paymentRequestToPaymentEntity(command);

    payment.setChargeId(paymentResult.paymentId());
    payment.setPaymentStatus(PaymentStatus.COMPLETED);

    return paymentRepository.save(payment);
  }

  @Override
  public Payment refundPayment(CancelPaymentCommand command) throws Exception {
    Payment payment = paymentRepository.findById(command.paymentId())
        .orElseThrow(() -> new PaymentNotFoundException(command.paymentId()));

    RefundResult refundResult = paymentStrategy.refundPayment(payment.getChargeId());

    payment.setRefundId(refundResult.refundId());
    payment.setPaymentStatus(PaymentStatus.CANCELLED);

    return paymentRepository.save(payment);
  }
}
