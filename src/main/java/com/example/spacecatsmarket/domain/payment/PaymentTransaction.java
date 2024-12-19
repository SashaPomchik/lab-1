package com.example.spacecatsmarket.domain.payment;

import com.example.spacecatsmarket.common.PaymentStatus;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class PaymentTransaction {

    UUID id;
    PaymentStatus status;
    String cartId;
}
