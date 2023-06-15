package com.mv.product.services.pricing.config;

import com.mv.product.services.pricing.strategy.DiscountType;
import java.math.BigDecimal;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "pricing")
public class PricingConfig {

  private DiscountConfig discountConfig;

  @Data
  public static class DiscountConfig {

    private DiscountType type;
    private CountBasedConfig countBasedConfig;
    private PercentageConfig percentageConfig;
  }

  @Data
  public static class CountBasedConfig {

    private Integer minItems;
    private BigDecimal discountValue;
    private BigDecimal maxDiscount;
    private BigDecimal discountIncrement;
  }

  @Data
  public static class PercentageConfig {

    private Integer minItems;
    private BigDecimal percentage;
  }

}
