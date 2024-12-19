package com.example.spacecatsmarket.config;

import com.example.spacecatsmarket.web.mapper.*;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MappersTestConfiguration {

    @Bean
    public PaymentServiceMapper paymentServiceMapper() {
        return Mappers.getMapper(PaymentServiceMapper.class);
    }

    @Bean
    public OrderDtoMapper orderDtoMapper() {
        return Mappers.getMapper(OrderDtoMapper.class);
    }

    @Bean
    public OrderEntryMapper orderEntryMapper() {
        return Mappers.getMapper(OrderEntryMapper.class);
    }

    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    @Bean
    public WishlistEntryMapper wishlistEntryMapper() {
        return Mappers.getMapper(WishlistEntryMapper.class);
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return Mappers.getMapper(CategoryMapper.class);
    }

    @Bean
    public CustomDetailsMapper customDetailsMapper() {
        return Mappers.getMapper(CustomDetailsMapper.class);
    }
}
