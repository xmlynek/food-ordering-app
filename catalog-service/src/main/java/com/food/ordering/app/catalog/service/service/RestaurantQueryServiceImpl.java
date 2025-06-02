package com.food.ordering.app.catalog.service.service;

import static com.food.ordering.app.catalog.service.service.RestaurantMenuItemServiceImpl.MENU_ITEM_RELATION_NAME;

import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.food.ordering.app.catalog.service.dto.BasicRestaurantDto;
import com.food.ordering.app.catalog.service.dto.FullRestaurantDto;
import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.catalog.service.mapper.RestaurantMapper;
import com.food.ordering.app.catalog.service.repository.RestaurantRepository;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
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
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantQueryServiceImpl implements RestaurantQueryService {

  private final RestaurantRepository restaurantRepository;
  private final RestaurantMapper restaurantMapper;
  private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;


  @Override
  public Mono<Page<BasicRestaurantDto>> findAllRestaurants(Pageable pageable, String searchValue) {
    // Filter for available restaurants.
    Query availabilityFilter = QueryBuilders.term(t -> t.field("isAvailable").value(true));

    // Text search on the restaurant's own fields (name and description).
    Query parentTextSearch = QueryBuilders.bool(b -> b
        .should(s -> s.match(m -> m.field("name").query(searchValue).analyzer("standard")))
        .should(s -> s.match(m -> m.field("description").query(searchValue).analyzer("standard")))
    );

    // Has-child query on the menu items.
    Query menuItemChildQuery = QueryBuilders.hasChild(h -> h
        .type(MENU_ITEM_RELATION_NAME)
        .query(q -> q.bool(b -> b
            .should(s -> s.wildcard(w -> w.field("name").wildcard("*" + searchValue + "*").caseInsensitive(true)))
            .should(s -> s.wildcard(w -> w.field("description").wildcard("*" + searchValue + "*").caseInsensitive(true)))
            .minimumShouldMatch("1")
        ))
        .scoreMode(ChildScoreMode.Max)
    );

    // Combine the queries: available restaurants that match either the parent fields or have a matching child.
    Query combinedQuery = QueryBuilders.bool(b -> b
        .filter(availabilityFilter)
        .must(QueryBuilders.bool(b2 -> b2
            .should(parentTextSearch)
            .should(menuItemChildQuery)
        ))
    );

    NativeQuery searchQuery = new NativeQueryBuilder()
        .withQuery(combinedQuery)
        .withPageable(pageable)
        .withTrackTotalHits(true)
        .build();

    return reactiveElasticsearchTemplate.searchForPage(searchQuery, Restaurant.class)
        .map(searchPage -> {
          List<BasicRestaurantDto> dtos = searchPage.getSearchHits().stream()
              .map(SearchHit::getContent)
              .map(restaurantMapper::restaurantToBasicRestaurantDto)
              .collect(Collectors.toList());
          return new PageImpl<>(dtos, pageable, searchPage.getTotalElements());
        });
  }

  @Override
  public Mono<FullRestaurantDto> findRestaurantById(String id) {
    return restaurantRepository.findById(id)
        .map(restaurantMapper::restaurantToFullRestaurantDto)
        .switchIfEmpty(Mono.error(new RestaurantNotFoundException(id)));
  }
}
