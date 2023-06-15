package com.mv.product.services;

import com.mv.product.services.pricing.Price;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  UUID sku;
  Price price;
  int quantity;
}
