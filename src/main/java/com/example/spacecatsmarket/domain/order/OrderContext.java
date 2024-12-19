package com.example.spacecatsmarket.domain.order;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OrderContext {

    String cartId;
    String customerReference;
    List<OrderEntry> entries;
    Double totalPrice;
}
