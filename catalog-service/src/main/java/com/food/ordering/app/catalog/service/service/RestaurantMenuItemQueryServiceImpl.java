package com.food.ordering.app.catalog.service.service;

import co.elastic.clients.elasticsearch._types.KnnQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.common.exception.MenuItemNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantMenuItemQueryServiceImpl implements RestaurantMenuItemQueryService {

  public static final String RESTAURANT_RELATION_NAME = "restaurant";

  private final ReactiveElasticsearchTemplate elasticsearchTemplate;

  @Override
  public Mono<Page<MenuItem>> findAllAvailableMenuItems(String restaurantId, Pageable pageable) {
    NativeQuery query = new NativeQueryBuilder()
        .withQuery(QueryBuilders.bool(b -> b
            .must(m -> m.term(t -> t.field("isAvailable").value(true)))
            .must(m -> m.hasParent(p -> p
                .parentType(RESTAURANT_RELATION_NAME)
                .query(q -> q.bool(b2 -> b2
                    .must(must1 -> must1.term(t -> t.field("_id").value(restaurantId)))
                    .must(must2 -> must2.term(t -> t.field("isAvailable").value(true)))
                ))
                .score(false)))))
        .withPageable(pageable)
        .withTrackTotalHits(true)
        .build();

    return elasticsearchTemplate.searchForPage(query, MenuItem.class)
        .map(searchPage -> {
          List<MenuItem> menuItems = searchPage.getSearchHits().stream()
              .map(SearchHit::getContent)
              .collect(Collectors.toList());
          return new PageImpl<>(menuItems, pageable, searchPage.getTotalElements());
        });
  }

  @Override
  public Mono<MenuItem> findMenuItemById(String restaurantId, String menuItemId) {
    NativeQuery query = new NativeQueryBuilder()
        .withQuery(QueryBuilders.bool(b -> b
            .must(m -> m.term(t -> t.field("_id").value(menuItemId)))
            .must(m -> m.hasParent(p -> p
                .parentType(RESTAURANT_RELATION_NAME)
                .query(q -> q.bool(b2 -> b2
                    .must(must1 -> must1.term(t -> t.field("_id").value(restaurantId)))
                    .must(must2 -> must2.term(t -> t.field("isAvailable").value(true)))
                ))
                .score(false)
            ))
        ))
        .build();

    return elasticsearchTemplate.search(query, MenuItem.class)
        .map(SearchHit::getContent)
        .singleOrEmpty()
        .switchIfEmpty(Mono.error(new MenuItemNotFoundException(menuItemId)));
  }

  @Override
  public Flux<MenuItem> findSimilarMenuItemsByEmbeddings(float[] imageEmbeddings) {
    log.info("Finding similar menu items by embeddings across all restaurants");

    Query query = QueryBuilders.bool(b -> b
        .must(m -> m.term(t -> t.field("isAvailable").value(true)))
        .must(m -> m.hasParent(p -> p
            .parentType(RESTAURANT_RELATION_NAME)
            .query(q -> q.term(t -> t.field("isAvailable").value(true)))
            .score(false)
        ))
    );

    NativeQuery nativeQuery = new NativeQueryBuilder()
        .withTrackTotalHits(true)
        .withMaxResults(10)
        .withKnnQuery(
            KnnQuery.of(builder -> builder
                .numCandidates(20)
                .k(10)
                .filter(query)
                .queryVector(
                    IntStream.range(0, imageEmbeddings.length).mapToObj(i -> imageEmbeddings[i])
                        .toList())
                .field("q768_image_embeddings")))
        .withExplain(true)
        .withMinScore(0.7f)
        .build();

    return elasticsearchTemplate.search(nativeQuery, MenuItem.class)
        .log()
        .map(SearchHit::getContent)
        .doOnError(
            e -> log.error("Error finding similar menu items by embeddings: {}", e.getMessage(),
                e));
  }
}
