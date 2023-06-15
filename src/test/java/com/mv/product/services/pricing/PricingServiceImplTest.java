package com.mv.product.services.pricing;

import com.mv.product.exceptions.NotFoundException;
import com.mv.product.persistence.entities.PriceEntity;
import com.mv.product.persistence.repositories.PriceRepository;
import com.mv.product.services.pricing.config.PricingConfig;
import com.mv.product.services.pricing.config.PricingConfig.DiscountConfig;
import com.mv.product.services.pricing.strategy.DiscountStrategy;
import com.mv.product.services.pricing.strategy.DiscountType;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PricingServiceImplTest {

  private final PriceRepository mockRepository = Mockito.mock(PriceRepository.class);
  private final PricingConfig mockConfig = Mockito.mock(PricingConfig.class);
  private final DiscountStrategy mockStrategy = Mockito.mock(DiscountStrategy.class);
  private PricingService service;

  @BeforeEach
  void setUp() {
    service = new PricingServiceImpl(mockConfig, mockStrategy, mockRepository);
  }

  @Test
  void testCalculatePrice_whenPriceNotExists_thenNotFoundException() {
    UUID sku = UUID.randomUUID();

    //when
    Mockito.when(mockRepository.findByProductSKU(sku)).thenReturn(Optional.empty());

    //then
    Assertions.assertThrowsExactly(NotFoundException.class,
        () -> service.calculatePrice(sku, 10));

    //verify
    Mockito.verify(mockRepository, Mockito.times(1)).findByProductSKU(sku);
    Mockito.verifyNoInteractions(mockConfig);
    Mockito.verifyNoInteractions(mockStrategy);
  }

  @Test
  void testCalculatePrice_whenPriceExists_thenValidPriceMappedCorrectly() {
    UUID sku = UUID.randomUUID();
    int quantity = 10;
    BigDecimal totalPrice = BigDecimal.TEN;
    BigDecimal ttlWDiscount = BigDecimal.valueOf(5);
    BigDecimal unitPrice = BigDecimal.ONE;
    DiscountType countBased = DiscountType.COUNT_BASED;

    //when
    Mockito.when(mockRepository.findByProductSKU(sku))
        .thenReturn(Optional.of(PriceEntity.builder()
            .sku(sku)
            .value(unitPrice)
            .build()));

    Mockito.when(mockConfig.getDiscountConfig())
        .thenReturn(getDiscountConfig(countBased));

    Mockito.when(mockStrategy.calculate(countBased, totalPrice, quantity))
        .thenReturn(ttlWDiscount);

    //then
    Price price = service.calculatePrice(sku, 10);

    //verify
    Mockito.verify(mockRepository, Mockito.times(1)).findByProductSKU(sku);
    Mockito.verify(mockConfig, Mockito.times(1)).getDiscountConfig();
    Mockito.verify(mockStrategy, Mockito.times(1)).calculate(countBased, totalPrice, quantity);

    Assertions.assertNotNull(price);
    Assertions.assertEquals(totalPrice.doubleValue(), price.getTotalPrice().doubleValue());
    Assertions.assertEquals(ttlWDiscount.doubleValue(), price.getTotalWithDiscount().doubleValue());
    Assertions.assertEquals(unitPrice.doubleValue(), price.getUnitPrice().doubleValue());
  }

  private DiscountConfig getDiscountConfig(DiscountType type) {
    DiscountConfig discountConfig = new DiscountConfig();
    discountConfig.setType(type);
    return discountConfig;
  }

}