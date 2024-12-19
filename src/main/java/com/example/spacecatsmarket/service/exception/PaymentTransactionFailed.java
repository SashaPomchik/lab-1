package com.example.spacecatsmarket.service.exception;

import java.util.UUID;

public class PaymentTransactionFailed extends RuntimeException {

    public static final String PAYMENT_TRANSACTION_WITH_ID_S_FOR_CART_ID_S_FAILED = "Payment transaction with id: %s, for cart id: %s FAILED";

    public PaymentTransactionFailed(UUID id, String cartId) {
        super(String.format(PAYMENT_TRANSACTION_WITH_ID_S_FOR_CART_ID_S_FAILED, id, cartId));
    }
}
