package com.food.ordering.app.order.service.repository;

import com.food.ordering.app.order.service.entity.Address;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
