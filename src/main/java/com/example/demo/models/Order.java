package com.example.demo.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@Value
public class Order {
    String id;
    String orderNumber;
    List<Product> products;
    Double totalPrice;
    Date orderDate;
}
