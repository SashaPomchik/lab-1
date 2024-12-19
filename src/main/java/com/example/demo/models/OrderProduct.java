package com.example.demo.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@Builder
public class OrderProduct {
    Long id;
    Order order;
    Product product;
    Integer quantity;
}
