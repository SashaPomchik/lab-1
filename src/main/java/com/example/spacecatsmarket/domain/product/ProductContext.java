package com.example.spacecatsmarket.domain.product;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductContext {

    String name;
    String description;
    Double price;
    String category;
    int stock;
}
