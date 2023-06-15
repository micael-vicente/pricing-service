package com.mv.product.services;

import java.util.UUID;

public interface ProductService {

  Product getPricedProduct(UUID sku, int quantity);

}
