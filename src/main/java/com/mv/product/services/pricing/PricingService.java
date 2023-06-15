package com.mv.product.services.pricing;

import java.util.UUID;

public interface PricingService {

  /**
   * Calculates the price of a product applying a discount if applicable.
   *
   * @param sku      the sku of the product
   * @param quantity the quantity wanted
   * @return the price components of this sku
   */
  Price calculatePrice(UUID sku, int quantity);
}
