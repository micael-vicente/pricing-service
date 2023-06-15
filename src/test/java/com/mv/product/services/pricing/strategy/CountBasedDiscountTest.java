package com.mv.product.services.pricing.strategy;

import com.mv.product.services.pricing.config.PricingConfig;
import com.mv.product.services.pricing.config.PricingConfig.CountBasedConfig;
import com.mv.product.services.pricing.config.PricingConfig.DiscountConfig;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CountBasedDiscountTest {

  @Test
  void testCalculate_whenOverMaxDiscount_thenMaxDiscount() {
    BigDecimal totalPrice = BigDecimal.valueOf(100);
    int quantity = 100;

    CountBasedDiscount service = new CountBasedDiscount(getConfig());

    BigDecimal calculate = service.calculate(totalPrice, quantity);

    Assertions.assertEquals(BigDecimal.valueOf(90), calculate);
  }

  @Test
  void testCalculate_whenQuantityEqualsMinItems_thenDiscountValue() {
    BigDecimal totalPrice = BigDecimal.valueOf(100);
    int quantity = 1;

    CountBasedDiscount service = new CountBasedDiscount(getConfig());

    BigDecimal calculate = service.calculate(totalPrice, quantity);

    Assertions.assertEquals(BigDecimal.valueOf(99), calculate);
  }

  @Test
  void testCalculate_whenQuantityGTMinItems_thenDiscountValuePlusExtra() {
    BigDecimal totalPrice = BigDecimal.valueOf(100);
    int quantity = 5;

    CountBasedDiscount service = new CountBasedDiscount(getConfig());

    BigDecimal calculate = service.calculate(totalPrice, quantity);

    Assertions.assertEquals(BigDecimal.valueOf(95), calculate);
  }

  private PricingConfig getConfig() {
    PricingConfig pricingConfig = new PricingConfig();
    DiscountConfig discountConfig = new DiscountConfig();

    CountBasedConfig countBasedConfig = new CountBasedConfig();
    countBasedConfig.setDiscountIncrement(BigDecimal.ONE);
    countBasedConfig.setMaxDiscount(BigDecimal.TEN);
    countBasedConfig.setDiscountValue(BigDecimal.ONE);
    countBasedConfig.setMinItems(1);

    discountConfig.setCountBasedConfig(countBasedConfig);
    discountConfig.setType(DiscountType.COUNT_BASED);
    pricingConfig.setDiscountConfig(discountConfig);

    return pricingConfig;
  }

}