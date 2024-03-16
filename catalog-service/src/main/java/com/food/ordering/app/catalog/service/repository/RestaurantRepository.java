package com.food.ordering.app.catalog.service.repository;

import com.food.ordering.app.catalog.service.entity.Restaurant;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Mono;

public interface RestaurantRepository extends ReactiveElasticsearchRepository<Restaurant, String> {

  Mono<Restaurant> findByIdAndIsAvailableTrue(String id);

}
