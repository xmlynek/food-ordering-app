package com.food.ordering.app.catalog.service.controller;

import com.food.ordering.app.catalog.service.dto.MenuItemDto;
import com.food.ordering.app.catalog.service.mapper.MenuItemMapper;
import com.food.ordering.app.catalog.service.service.RestaurantMenuItemQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class SimilarMenuItemsSearchController {

  private final RestaurantMenuItemQueryService menuItemQueryService;
  private final MenuItemMapper menuItemMapper;


  @PostMapping("/search-by-image")
  public Flux<MenuItemDto> searchMenuItemsByImageEmbeddings(@RequestBody float[] imageEmbeddings) {
    log.info("Searching menu items by image embeddings");
    return menuItemQueryService.findSimilarMenuItemsByEmbeddings(imageEmbeddings)
        .map(menuItemMapper::menuItemToMenuItemDto);
  }
}
