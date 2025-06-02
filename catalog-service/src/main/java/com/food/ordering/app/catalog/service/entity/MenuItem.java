package com.food.ordering.app.catalog.service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.JoinTypeRelation;
import org.springframework.data.elasticsearch.annotations.JoinTypeRelations;
import org.springframework.data.elasticsearch.core.join.JoinField;

@Document(indexName = "restaurants")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

  @Id
  private String id;

  @Field(type = FieldType.Keyword)
  private String restaurantId;

  @Field(type = FieldType.Text)
  private String name;

  @Field(type = FieldType.Text)
  private String description;

  @Field(type = FieldType.Double)
  private BigDecimal price;

  @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
  private LocalDateTime createdAt;

  @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
  private LocalDateTime lastModifiedAt;

  @Field(type = FieldType.Boolean)
  private Boolean isAvailable;

  @Field(type = FieldType.Text)
  private String imageUrl;

  @Field(name = "join_field")
  @JoinTypeRelations(relations = @JoinTypeRelation(parent = "restaurant", children = {"menu_item"}))
  private JoinField<String> joinField;
}
