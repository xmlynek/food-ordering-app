package com.food.ordering.app.kitchen.service.repository.specification;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class KitchenTicketSpecifications {

  private static final String RESTAURANT_ROOT_ID_FIELD = "id";
  private static final String RESTAURANT_ROOT_OWNER_ID_FIELD = "ownerId";
  private static final String RESTAURANT_ROOT_NAME = "restaurant";
  private static final String STATUS_FIELD = "status";

  public static Specification<KitchenTicket> withRestaurantIdAndRestaurantOwnerIdAndOptionalStatus(
      UUID restaurantId, String ownerId, KitchenTicketStatus status) {

    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Join KitchenTicket with Restaurant to filter by Restaurant fields
      var restaurantRoot = root.join(RESTAURANT_ROOT_NAME);

      predicates.add(criteriaBuilder.equal(restaurantRoot.get(RESTAURANT_ROOT_ID_FIELD), restaurantId));
      predicates.add(criteriaBuilder.equal(restaurantRoot.get(RESTAURANT_ROOT_OWNER_ID_FIELD), ownerId));

      if (status != null) {
        predicates.add(criteriaBuilder.equal(root.get(STATUS_FIELD), status));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
