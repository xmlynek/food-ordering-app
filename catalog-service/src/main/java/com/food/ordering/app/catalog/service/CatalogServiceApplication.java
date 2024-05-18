package com.food.ordering.app.catalog.service;

import com.food.ordering.app.common.config.JsonObjectMapperJavaTimeModuleConfiguration;
import io.eventuate.tram.spring.flyway.EventuateTramFlywayMigrationConfiguration;
import io.eventuate.tram.spring.reactive.consumer.jdbc.ReactiveTransactionalNoopDuplicateMessageDetectorConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@Import({
    EventuateTramFlywayMigrationConfiguration.class,
    JsonObjectMapperJavaTimeModuleConfiguration.class,
    ReactiveTransactionalNoopDuplicateMessageDetectorConfiguration.class // TODO: Remove when issue is fixed https://github.com/eventuate-tram/eventuate-tram-sagas/issues/101
})
public class CatalogServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CatalogServiceApplication.class, args);
  }
}
