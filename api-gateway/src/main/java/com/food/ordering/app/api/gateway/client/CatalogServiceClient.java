package com.food.ordering.app.api.gateway.client;

import com.food.ordering.app.api.gateway.dto.MenuItemDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

@ReactiveFeignClient(name = "catalog-service", url = "${CATALOG_SERVICE_URI}")
public interface CatalogServiceClient {

  @PostMapping(value = "/api/catalog/search-by-image", consumes = MediaType.APPLICATION_JSON_VALUE)
  Flux<MenuItemDto> findSimilarMenuItems(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader,
      @RequestBody float[] imageEmbeddings);
}
