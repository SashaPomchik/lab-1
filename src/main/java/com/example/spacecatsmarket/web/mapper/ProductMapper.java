package com.example.spacecatsmarket.web.mapper;

import com.example.spacecatsmarket.domain.product.Product;
import com.example.spacecatsmarket.dto.product.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "stock", source = "product.stock")
    @Mapping(target = "category", source = "product.category")
    ProductDto toProductDto(Product product);

    @Mapping(target = "id", source = "productDto.id")
    @Mapping(target = "name", source = "productDto.name")
    @Mapping(target = "description", source = "productDto.description")
    @Mapping(target = "price", source = "productDto.price")
    @Mapping(target = "stock", source = "productDto.stock")
    @Mapping(target = "category", source = "productDto.category")
    Product toProduct(ProductDto productDto);
}
