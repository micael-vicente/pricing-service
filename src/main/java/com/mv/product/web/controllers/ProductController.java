package com.mv.product.web.controllers;

import com.mv.product.mappers.Service2WebMapper;
import com.mv.product.services.Product;
import com.mv.product.services.ProductService;
import com.mv.product.web.dtos.ProductDTO;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;
  private final Service2WebMapper service2WebMapper;

  @GetMapping("/{productId}")
  public ResponseEntity<ProductDTO> getPricedProduct(
      @PathVariable UUID productId,
      @RequestParam(required = false, name = "quantity", defaultValue = "1") Integer quantity) {

    Product pricedProduct = productService.getPricedProduct(productId, quantity);
    return ResponseEntity.ok(service2WebMapper.toProductDTO(pricedProduct));
  }

}
