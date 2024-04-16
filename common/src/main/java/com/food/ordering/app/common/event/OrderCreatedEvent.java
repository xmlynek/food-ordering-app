package com.food.ordering.app.common.event;


import com.food.ordering.app.common.dto.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class OrderCreatedEvent {

  private UUID orderId;
  private UUID customerId;
  private UUID restaurantId;
  private BigDecimal price;
  private String street;
  private String city;
  private String postalCode;
  private List<Product> items;

}
