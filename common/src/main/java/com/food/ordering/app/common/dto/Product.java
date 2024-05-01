package com.food.ordering.app.common.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Product {

  private final UUID productId;
  private final Integer quantity;
  private final BigDecimal price;
}
