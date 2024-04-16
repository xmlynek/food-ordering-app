package com.food.ordering.app.catalog.service.config;

import io.eventuate.common.jdbc.OutboxPartitioningSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventuateJdbcConfig {

  @Bean
  public OutboxPartitioningSpec outboxPartitioningSpec() {
    return OutboxPartitioningSpec.DEFAULT;
  }
}
