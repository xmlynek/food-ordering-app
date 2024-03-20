package com.food.ordering.app.payment.service.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PaymentGatewayConfig {

  private final AppConfigurationProperties appConfigurationProperties;

  @PostConstruct
  public void init() {
    Stripe.apiKey = appConfigurationProperties.getStripeApiKey();
  }
}
