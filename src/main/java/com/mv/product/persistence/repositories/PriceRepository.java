package com.mv.product.persistence.repositories;

import com.mv.product.persistence.entities.PriceEntity;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class PriceRepository {

  /**
   * Finds a price by product SKU.
   *
   * @param sku the product's SKU
   * @return optional that contains the price if found
   */
  public Optional<PriceEntity> findByProductSKU(UUID sku) {
    Random r = new Random();
    PriceEntity price = PriceEntity.builder()
        .sku(sku)
        .value(BigDecimal.valueOf(r.nextDouble(10.0, 100.0)))
        .build();

    return Optional.of(price);
  }

}
