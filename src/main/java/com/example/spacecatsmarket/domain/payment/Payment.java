package com.example.spacecatsmarket.domain.payment;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Payment {

    String consumerReference;
    String cartId;
    UUID paymentAssetId;
    double amount;

}
