package com.mv.product.services;

import com.mv.product.exceptions.NotFoundException;
import com.mv.product.persistence.entities.ProductEntity;
import com.mv.product.persistence.repositories.ProductRepository;
import com.mv.product.services.pricing.PricingService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final PricingService pricing;
  private final ProductRepository repository;

  @Override
  public Product getPricedProduct(UUID sku, int quantity) {
    ProductEntity entity = repository.findProductBySKU(sku)
        .orElseThrow(() -> new NotFoundException("Product with sku " + sku + " not found"));

    return Product.builder()
        .sku(entity.getSku())
        .quantity(quantity)
        .price(pricing.calculatePrice(sku, quantity))
        .build();
  }
}
