package com.example.demo.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Builder
@Value
public class Product {
    Long id;
    String name;
    String description;
    Double price;
    Boolean available;
    Category category;
}
