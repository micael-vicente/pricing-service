package com.mv.product.services.pricing.strategy;

import com.mv.product.services.pricing.config.PricingConfig;
import com.mv.product.services.pricing.config.PricingConfig.PercentageConfig;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PercentageDiscount implements DiscountPolicy {

  private final PricingConfig config;

  @Override
  public BigDecimal calculate(BigDecimal totalPrice, int quantity) {
    PercentageConfig props = config.getDiscountConfig().getPercentageConfig();
    BigDecimal discount = BigDecimal.ZERO;

    if (quantity >= props.getMinItems()) {
      discount = props.getPercentage()
          .divide(BigDecimal.valueOf(100.0), 2, RoundingMode.HALF_UP)
          .multiply(totalPrice);
    }

    return totalPrice.subtract(discount);
  }

  @Override
  public DiscountType getType() {
    return DiscountType.PERCENTAGE;
  }
}
