package com.mv.product.persistence.entities;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceEntity {
  private UUID sku;
  private BigDecimal value;
}
