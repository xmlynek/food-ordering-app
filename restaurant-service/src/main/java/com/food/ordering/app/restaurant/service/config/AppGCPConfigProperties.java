package com.food.ordering.app.restaurant.service.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@Getter
@AllArgsConstructor
@ConfigurationPropertiesBinding
@ConfigurationProperties(prefix = "app.gcp.storage")
public class AppGCPConfigProperties {

  private final String bucketName;

}
