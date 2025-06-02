package com.food.ordering.app.catalog.service.repository;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface MenuItemRepository extends ReactiveElasticsearchRepository<MenuItem, String> {

}
