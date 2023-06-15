package com.mv.product.services.pricing;

import java.util.UUID;

public interface PricingService {

  Price calculatePrice(UUID sku, int quantity);
}
