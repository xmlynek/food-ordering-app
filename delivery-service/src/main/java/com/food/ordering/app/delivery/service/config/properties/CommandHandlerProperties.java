package com.food.ordering.app.delivery.service.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.properties.command.handler")
@ConfigurationPropertiesBinding
public class CommandHandlerProperties {

  private final String channel;
}
