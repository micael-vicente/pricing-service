package com.mv.product.services;

import java.util.UUID;

public interface ProductService {

  /**
   * Retrieve a priced product given an sku and a quantity
   *
   * @param sku      the sku to look for
   * @param quantity the quantity of SKU wanted
   * @return the product with priced calculated
   */
  Product getPricedProduct(UUID sku, int quantity);

}
