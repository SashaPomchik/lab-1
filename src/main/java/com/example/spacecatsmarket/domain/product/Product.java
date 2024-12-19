package com.example.spacecatsmarket.domain.product;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Product {

    String id;
    String name;
    String description;
    Double price;
    String category;
    int stock;
}
