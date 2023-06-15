package com.mv.product.services.pricing.strategy;

import java.math.BigDecimal;

public interface DiscountPolicy {

  /**
   * Calculates the final price according to policy.
   *
   * @param price    the base price
   * @param quantity the quantity
   * @return a price according to policy configured
   */
  BigDecimal calculate(BigDecimal price, int quantity);

  /**
   * The type of discount provided by this policy.
   *
   * @return enum identifying the type of discount
   */
  DiscountType getType();

}
