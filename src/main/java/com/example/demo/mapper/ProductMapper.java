package com.example.demo.mapper;

import com.example.demo.dto.ProductDTO;
import com.example.demo.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductDTO dto);

    @Mapping(source = "category.id", target = "categoryId")
    ProductDTO toDto(Product product);
}