package com.mv.product.services.pricing.strategy;

import com.mv.product.services.pricing.config.PricingConfig;
import com.mv.product.services.pricing.config.PricingConfig.CountBasedConfig;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountBasedDiscount implements DiscountPolicy {

  private final PricingConfig config;

  @Override
  public BigDecimal calculate(BigDecimal totalPrice, int quantity) {
    BigDecimal discount = calculateDiscount(quantity);

    return totalPrice.subtract(discount);
  }

  @Override
  public DiscountType getType() {
    return DiscountType.COUNT_BASED;
  }

  private BigDecimal calculateDiscount(int quantity) {
    CountBasedConfig props = config.getDiscountConfig().getCountBasedConfig();
    BigDecimal discount = BigDecimal.ZERO;

    if (quantity >= props.getMinItems()) {
      BigDecimal eligibleQuantity = BigDecimal.valueOf(quantity - props.getMinItems());
      discount = props.getDiscountValue()
          .add(props.getDiscountIncrement().multiply(eligibleQuantity));
    }

    return discount.min(props.getMaxDiscount());
  }
}
