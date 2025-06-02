package com.food.ordering.app.api.gateway;

import com.food.ordering.app.api.gateway.client.CatalogServiceClient;
import com.food.ordering.app.api.gateway.client.EmbeddingsServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients(clients = {CatalogServiceClient.class, EmbeddingsServiceClient.class})
public class ApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }
}
