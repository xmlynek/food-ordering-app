package com.food.ordering.app.order.service.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.properties.command.destination")
@ConfigurationPropertiesBinding
public class CommandDestinationProperties {

  private final String kitchenService;

  private final String paymentService;
}
