package com.mv.product.web.dtos;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceDTO {

  private BigDecimal unitPrice;
  private BigDecimal totalPrice;
  private BigDecimal totalWithDiscount;
}
