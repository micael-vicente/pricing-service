package com.mv.product.mappers;

import com.mv.product.services.Product;
import com.mv.product.web.dtos.ProductDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface Service2WebMapper {

  ProductDTO toProductDTO(Product pojo);
}
