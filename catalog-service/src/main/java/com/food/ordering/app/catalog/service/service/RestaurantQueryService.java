package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.dto.BasicRestaurantDto;
import com.food.ordering.app.catalog.service.dto.FullRestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface RestaurantQueryService {

  Mono<Page<BasicRestaurantDto>> findAllRestaurants(Pageable pageable, String searchValue);

  Mono<FullRestaurantDto> findRestaurantById(String id);

}
