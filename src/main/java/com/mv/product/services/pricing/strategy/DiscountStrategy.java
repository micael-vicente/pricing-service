package com.mv.product.services.pricing.strategy;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class DiscountStrategy {

  private final EnumMap<DiscountType, DiscountPolicy> discountPolicies;

  public DiscountStrategy(Set<DiscountPolicy> policies) {
    discountPolicies = new EnumMap<>(DiscountType.class);
    policies.forEach(policy -> discountPolicies.put(policy.getType(), policy));
  }

  public BigDecimal calculate(DiscountType type, BigDecimal price, int quantity) {
    return discountPolicies.get(type).calculate(price, quantity);
  }
}
