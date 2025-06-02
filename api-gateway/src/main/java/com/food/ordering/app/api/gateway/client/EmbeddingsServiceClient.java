package com.food.ordering.app.api.gateway.client;

import com.food.ordering.app.api.gateway.dto.EmbeddingsResponse;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "embeddings-service", url = "${EMBEDDINGS_SERVICE_URI}")
public interface EmbeddingsServiceClient {

  @PostMapping(value = "/api/embeddings", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  Mono<EmbeddingsResponse> getEmbeddings(MultiValueMap<String, Object> formData);
}
