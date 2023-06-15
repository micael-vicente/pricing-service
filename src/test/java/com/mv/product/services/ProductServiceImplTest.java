package com.mv.product.services;

import com.mv.product.exceptions.NotFoundException;
import com.mv.product.persistence.entities.ProductEntity;
import com.mv.product.persistence.repositories.ProductRepository;
import com.mv.product.services.pricing.Price;
import com.mv.product.services.pricing.PricingService;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProductServiceImplTest {

  private final PricingService mockPricing = Mockito.mock(PricingService.class);
  private final ProductRepository mockRepository = Mockito.mock(ProductRepository.class);
  private ProductService service;

  @BeforeEach
  void setUp() {
    service = new ProductServiceImpl(mockPricing, mockRepository);
  }

  @Test
  void testGetPricedProduct_whenProductNotExists_thenNotFoundException() {
    UUID sku = UUID.randomUUID();

    //when
    Mockito.when(mockRepository.findProductBySKU(sku)).thenReturn(Optional.empty());

    //then
    Assertions.assertThrowsExactly(NotFoundException.class,
        () -> service.getPricedProduct(sku, 10));

    //verify
    Mockito.verify(mockRepository, Mockito.times(1)).findProductBySKU(sku);
    Mockito.verifyNoInteractions(mockPricing);
  }

  @Test
  void testGetPricedProduct_whenProductExists_thenValidProductMappedCorrectly() {
    UUID sku = UUID.randomUUID();
    int quantity = 10;
    BigDecimal totalPrice = BigDecimal.TEN;
    BigDecimal totalWithDiscount = BigDecimal.valueOf(5);
    BigDecimal unitPrice = BigDecimal.ONE;

    //when
    Mockito.when(mockRepository.findProductBySKU(sku))
        .thenReturn(Optional.of(ProductEntity.builder()
            .sku(sku)
            .build()));

    Mockito.when(mockPricing.calculatePrice(sku, quantity))
        .thenReturn(Price.builder()
            .totalPrice(totalPrice)
            .totalWithDiscount(totalWithDiscount)
            .unitPrice(unitPrice)
            .build());

    //then
    Product pricedProduct = service.getPricedProduct(sku, quantity);

    //verify
    Mockito.verify(mockRepository, Mockito.times(1)).findProductBySKU(sku);
    Mockito.verify(mockPricing, Mockito.times(1)).calculatePrice(sku, quantity);

    Assertions.assertEquals(quantity, pricedProduct.getQuantity());
    Assertions.assertEquals(sku, pricedProduct.getSku());
    Assertions.assertNotNull(pricedProduct.getPrice());
    Assertions.assertEquals(totalPrice, pricedProduct.getPrice().getTotalPrice());
    Assertions.assertEquals(totalWithDiscount, pricedProduct.getPrice().getTotalWithDiscount());
    Assertions.assertEquals(unitPrice, pricedProduct.getPrice().getUnitPrice());
  }

}