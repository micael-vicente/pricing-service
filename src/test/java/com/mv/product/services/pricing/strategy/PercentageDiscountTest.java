package com.mv.product.services.pricing.strategy;

import com.mv.product.services.pricing.config.PricingConfig;
import com.mv.product.services.pricing.config.PricingConfig.DiscountConfig;
import com.mv.product.services.pricing.config.PricingConfig.PercentageConfig;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PercentageDiscountTest {

  @Test
  void testCalculate_whenQuantityEqualsMinItems_thenApplyDiscount() {
    BigDecimal totalPrice = BigDecimal.valueOf(100);
    int quantity = 5;

    PercentageDiscount service = new PercentageDiscount(getConfig());

    BigDecimal calculate = service.calculate(totalPrice, quantity);

    Assertions.assertEquals(BigDecimal.valueOf(90).setScale(2, RoundingMode.HALF_UP), calculate);
  }

  private PricingConfig getConfig() {
    PricingConfig pricingConfig = new PricingConfig();
    DiscountConfig discountConfig = new DiscountConfig();

    PercentageConfig percentageConfig = new PercentageConfig();
    percentageConfig.setPercentage(BigDecimal.TEN);
    percentageConfig.setMinItems(5);

    discountConfig.setType(DiscountType.PERCENTAGE);
    discountConfig.setPercentageConfig(percentageConfig);
    pricingConfig.setDiscountConfig(discountConfig);

    return pricingConfig;
  }

}