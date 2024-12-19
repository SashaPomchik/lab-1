package com.example.spacecatsmarket.dto.payment;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class PaymentClientRequestDto {

    String consumerReference;
    UUID paymentAssetId;
    double amount;
}
