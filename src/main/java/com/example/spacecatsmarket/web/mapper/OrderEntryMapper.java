package com.example.spacecatsmarket.web.mapper;

import com.example.spacecatsmarket.common.ProductType;
import com.example.spacecatsmarket.domain.order.OrderEntry;
import com.example.spacecatsmarket.dto.order.OrderEntryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = ProductType.class)
public interface OrderEntryMapper {

    @Mapping(target = "productType", expression = "java(ProductType.fromDisplayName(orderEntryDto.getProductType()))")
    @Mapping(target = "amount", source = "amount")
    OrderEntry toOrderEntry(OrderEntryDto orderEntryDto);
}
