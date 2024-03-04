package com.food.ordering.app.payment.service.payment.strategy;

import com.food.ordering.app.payment.service.payment.model.dto.PaymentRequest;
import com.food.ordering.app.payment.service.payment.model.dto.PaymentResult;
import com.food.ordering.app.payment.service.payment.model.dto.RefundResult;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.RefundCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StripePaymentStrategy implements PaymentStrategy {

  @PostConstruct
  public void init() {
    // TODO: use configuration
    Stripe.apiKey = "sk_test_51Oc4G2Hg2RuOlHnDmj9OJ8NPORZpLpqHPg9kJN0msWpiTS9d3kfDj86sOoMyIBnppg0dGN80LyUODIzDZrn9xJDO00Lco5Q5f1";
  }

  @Override
  public PaymentResult processPayment(PaymentRequest paymentRequest) throws StripeException {
    log.info("Processing payment with Stripe started");

    ChargeCreateParams chargeCreateParams = ChargeCreateParams.builder()
        .setAmount(Long.parseLong(paymentRequest.amount().getAmount().toString().replace(".", "")))
        .setCurrency(paymentRequest.currency().name())
        .setDescription(paymentRequest.description())
        .setSource(paymentRequest.paymentToken())
        .build();

    Charge charge = Charge.create(chargeCreateParams);

    PaymentResult paymentResult = new PaymentResult(charge.getId(), charge.getStatus());
    log.info("Stripe Payment succeeded, chargeID {}", paymentResult.paymentId());

    return paymentResult;
  }

  @Override
  public RefundResult refundPayment(String paymentId) throws Exception {
    log.info("Refunding payment with Stripe for payment {} started", paymentId);
    RefundCreateParams params = RefundCreateParams.builder()
        .setCharge(paymentId)
        .build();
    Refund refund = Refund.create(params);

    log.info("Stripe refund for payment {} succeeded, refund id = {}", paymentId, refund.getId());
    return new RefundResult(refund.getId(), refund.getStatus());
  }
}
