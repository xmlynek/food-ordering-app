package com.food.ordering.app.payment.service.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@Getter
@AllArgsConstructor
@ConfigurationPropertiesBinding
@ConfigurationProperties(prefix = "app.properties")
public class AppConfigurationProperties {

  private final String stripeApiKey;

}
