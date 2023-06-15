package com.mv.product.persistence.repositories;

import com.mv.product.persistence.entities.ProductEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

  /**
   * Finds a product by SKU.
   *
   * @param sku the sku to look for
   * @return optional that contains the product if found
   */
  public Optional<ProductEntity> findProductBySKU(UUID sku) {
    ProductEntity product = ProductEntity.builder()
        .sku(sku)
        .build();

    return Optional.of(product);
  }

}
