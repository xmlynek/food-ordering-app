package com.food.ordering.app.order.service.principal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalProviderImpl implements PrincipalProvider {

  @Override
  public String getName() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
