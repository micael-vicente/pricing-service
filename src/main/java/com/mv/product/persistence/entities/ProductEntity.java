package com.mv.product.persistence.entities;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductEntity {

  private UUID sku;
}
