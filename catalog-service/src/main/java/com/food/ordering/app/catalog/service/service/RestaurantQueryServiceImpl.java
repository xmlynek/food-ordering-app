package com.food.ordering.app.catalog.service.service;

import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.food.ordering.app.catalog.service.dto.BasicRestaurantDto;
import com.food.ordering.app.catalog.service.dto.FullRestaurantDto;
import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.catalog.service.mapper.RestaurantMapper;
import com.food.ordering.app.catalog.service.repository.RestaurantRepository;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.support.PageableExecutionUtils;
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
    var criteria = QueryBuilders.bool()
        .filter(QueryBuilders.term().field("isAvailable").value(true).build()._toQuery())
        .filter(QueryBuilders.bool()
            .should(QueryBuilders.wildcard().field("name").wildcard("*" + searchValue + "*").build()
                ._toQuery())
            .should(QueryBuilders.nested().path("menuItems").query(QueryBuilders.bool()
                    .should(
                        QueryBuilders.wildcard().field("menuItems.name")
                            .wildcard("*" + searchValue + "*").build()
                            ._toQuery())
                    .should(
                        QueryBuilders.wildcard().field("menuItems.description")
                            .wildcard("*" + searchValue + "*").build()
                            ._toQuery())
                    .minimumShouldMatch("1")
                    .build()._toQuery()).scoreMode(ChildScoreMode.Max)
                .build()._toQuery())
            .build()._toQuery())
        .build()._toQuery();

    NativeQuery searchQuery = new NativeQueryBuilder()
        .withPageable(pageable)
        .withQuery(criteria)
        .build();

    return reactiveElasticsearchTemplate.searchForPage(searchQuery, Restaurant.class)
        .map(pageResult -> PageableExecutionUtils.getPage(
            pageResult.getContent().stream().map(SearchHit::getContent)
                .map(restaurantMapper::restaurantToBasicRestaurantDto).collect(
                    Collectors.toList()),
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),
            pageResult::getTotalPages)
        );
  }
  @Override
  public Mono<FullRestaurantDto> findRestaurantById(String id) {
    return restaurantRepository.findById(id)
        .map(restaurantMapper::restaurantToFullRestaurantDto)
        .switchIfEmpty(Mono.error(new RestaurantNotFoundException(id)));
  }
}
