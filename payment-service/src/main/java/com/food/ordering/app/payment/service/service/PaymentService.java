package com.food.ordering.app.payment.service.service;

import com.food.ordering.app.common.command.CancelPaymentCommand;
import com.food.ordering.app.common.command.ProcessPaymentCommand;
import com.food.ordering.app.payment.service.entity.Payment;

public interface PaymentService {

  Payment saveFailedPayment(ProcessPaymentCommand command);

  Payment processPayment(ProcessPaymentCommand command) throws Exception;

  Payment refundPayment(CancelPaymentCommand command) throws Exception;
}
