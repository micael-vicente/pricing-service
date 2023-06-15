package com.mv.product.services.pricing;

import com.mv.product.exceptions.NotFoundException;
import com.mv.product.persistence.repositories.PriceRepository;
import com.mv.product.services.pricing.config.PricingConfig;
import com.mv.product.services.pricing.strategy.DiscountStrategy;
import com.mv.product.services.pricing.strategy.DiscountType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

  private final PricingConfig config;
  private final DiscountStrategy strategy;
  private final PriceRepository repository;

  @Override
  public Price calculatePrice(UUID sku, int quantity) {
    BigDecimal unitPrice = repository.findByProductSKU(sku)
        .orElseThrow(() -> new NotFoundException("Price for sku " + sku + " not found"))
        .getValue();
    
    DiscountType type = config.getDiscountConfig().getType();

    BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    BigDecimal totalWithDiscount = strategy.calculate(type, totalPrice, quantity);

    return Price.builder()
        .unitPrice(unitPrice.setScale(2, RoundingMode.HALF_UP))
        .totalPrice(totalPrice.setScale(2, RoundingMode.HALF_UP))
        .totalWithDiscount(totalWithDiscount.setScale(2, RoundingMode.HALF_UP))
        .build();
  }
}
