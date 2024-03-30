package com.food.ordering.app.kitchen.service.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.properties.saga.command.handler")
@ConfigurationPropertiesBinding
public class SagaCommandHandlerProperties {

  private final String channel;
}
