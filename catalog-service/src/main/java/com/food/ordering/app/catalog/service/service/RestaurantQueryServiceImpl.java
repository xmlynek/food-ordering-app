package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.catalog.service.repository.RestaurantRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantQueryServiceImpl implements RestaurantQueryService {

  private final RestaurantRepository restaurantRepository;
  private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;
  private final ReactiveElasticsearchOperations reactiveElasticsearchOperations;


  @Override
  public Mono<Page<Restaurant>> findAllRestaurants(Pageable pageable) {
    NativeQuery searchQuery = new NativeQueryBuilder()
        .withPageable(pageable)
        .build();

    return reactiveElasticsearchTemplate.searchForPage(searchQuery, Restaurant.class)
        .map(pageResult -> PageableExecutionUtils.getPage(
            pageResult.getContent().stream().map(SearchHit::getContent).collect(
                Collectors.toList()),
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),
            pageResult::getTotalPages)
        );
  }

  @Override
  public Mono<Restaurant> findRestaurantById(String id) {
    return restaurantRepository.findById(id);
  }

  @Override
  public Flux<MenuItem> findAllMenuItems(String restaurantId) {
    return restaurantRepository.findById(restaurantId)
        .flatMapMany(restaurant -> Flux.fromIterable(restaurant.getMenuItems()));
  }

  @Override
  public Mono<MenuItem> findMenuItemById(String restaurantId, String menuItemId) {
    return restaurantRepository.findById(restaurantId)
        .flatMap(restaurant -> Flux.fromIterable(restaurant.getMenuItems())
            .filter(menuItem -> menuItem.getId().equals(menuItemId))
            .singleOrEmpty());
  }

}
