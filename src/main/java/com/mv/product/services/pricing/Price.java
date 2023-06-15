package com.mv.product.services.pricing;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Price {

  private BigDecimal unitPrice;
  private BigDecimal totalPrice;
  private BigDecimal totalWithDiscount;

}
