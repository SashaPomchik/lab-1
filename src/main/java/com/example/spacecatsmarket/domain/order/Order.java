package com.example.spacecatsmarket.domain.order;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Order {

    String id;
    UUID transactionId;
    List<OrderEntry> entries;
    String cartId;
    String consumerReference;
    Double totalPrice;


}
