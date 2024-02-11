package com.food.ordering.app.restaurant.service.repository;

import com.food.ordering.app.restaurant.service.entity.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
