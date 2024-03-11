package com.food.ordering.app.catalog.service.repository;

import com.food.ordering.app.catalog.service.entity.Restaurant;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface RestaurantRepository extends ReactiveElasticsearchRepository<Restaurant, String> {


}
