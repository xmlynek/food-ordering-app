package com.food.ordering.app.api.gateway.handler;

import com.food.ordering.app.api.gateway.client.CatalogServiceClient;
import com.food.ordering.app.api.gateway.client.EmbeddingsServiceClient;
import com.food.ordering.app.api.gateway.dto.MenuItemDto;
import feign.FeignException;
import java.time.Duration;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
@Slf4j
@RequiredArgsConstructor
public class SimilarMenuItemsHandler {

  private final EmbeddingsServiceClient embeddingsServiceClient;
  private final CatalogServiceClient catalogServiceClient;

  public Mono<ServerResponse> findSimilarMenuItems(ServerRequest request) {
    String authHeader = request.headers().firstHeader(HttpHeaders.AUTHORIZATION);

    Flux<MenuItemDto> resultFlux = request.multipartData()
        .flatMap(multipart -> {
          // Get the file part named "image"
          var part = multipart.getFirst("image");
          if (part == null || !(part instanceof FilePart)) {
            return Mono.error(new IllegalArgumentException("No file part named 'image'"));
          }
          FilePart filePart = (FilePart) part;
          // Join all DataBuffers from the file content
          return DataBufferUtils.join(filePart.content())
              .map(dataBuffer -> {
                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(bytes);
                DataBufferUtils.release(dataBuffer);
                // Wrap the bytes in a ByteArrayResource with a filename
                Resource resource = new ByteArrayResource(bytes) {
                  @Override
                  public String getFilename() {
                    return filePart.filename();
                  }
                };
                // Build a MultiValueMap with the key "image"
                MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
                formData.add("image", resource);
                return formData;
              });
        })
        .flatMap(formData -> {
          log.info("Calling embeddings service with multipart data");
          return embeddingsServiceClient.getEmbeddings(formData)
              .timeout(Duration.ofSeconds(10))
              .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                  .jitter(0.5)
                  .doBeforeRetry(rs -> log.warn("Retrying embeddings service call, attempt: {}", rs.totalRetries() + 1)));
        })
        .flatMapMany(embeddingsResponse -> {
          log.info("Received embeddings; calling catalog service for similar menu items");
          return catalogServiceClient.findSimilarMenuItems(authHeader, embeddingsResponse.embeddings())
              .timeout(Duration.ofSeconds(10))
              .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                  .jitter(0.5)
                  .doBeforeRetry(rs -> log.warn("Retrying catalog service call, attempt: {}", rs.totalRetries() + 1)));
        });

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(resultFlux, MenuItemDto.class)
        .onErrorResume(this::handleError);
  }

  private Mono<ServerResponse> handleError(Throwable error) {
    if (error instanceof FeignException feignEx) {
      if (feignEx.status() == HttpStatus.UNAUTHORIZED.value()) {
        log.warn("Received 401 Unauthorized from downstream service: {}", feignEx.contentUTF8());
        return ServerResponse.status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(Map.of("error", feignEx.contentUTF8()));
      }
    }
    log.error("Unexpected error in findSimilarMenuItems", error);
    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(Map.of("error", "Failed to find similar menu items: " + error.getMessage()));
  }
}
