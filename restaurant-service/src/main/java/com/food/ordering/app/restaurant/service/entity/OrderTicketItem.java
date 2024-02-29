package com.food.ordering.app.restaurant.service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_ticket_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderTicketItem {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, updatable = false)
  private Integer quantity;

  @Column(nullable = false, updatable = false)
  private BigDecimal price;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "order_ticket_id", updatable = false, nullable = false)
  private RestaurantOrderTicket orderTicket;

  @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
  @JoinColumn(name = "menu_item_id", updatable = false, nullable = false)
  private MenuItem menuItem;

}
