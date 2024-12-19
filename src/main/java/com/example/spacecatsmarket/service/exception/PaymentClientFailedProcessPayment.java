package com.example.spacecatsmarket.service.exception;

public class PaymentClientFailedProcessPayment extends RuntimeException {

    private static final String FAILED_TO_PROCESS_PAYMENT_FOR_CART_ID_S_AND_CONSUMER_REFERENCE_S =
        "Failed to process payment for cart id %s and consumer reference %s";

    public PaymentClientFailedProcessPayment(String cartId, String consumerReference) {
        super(String.format(FAILED_TO_PROCESS_PAYMENT_FOR_CART_ID_S_AND_CONSUMER_REFERENCE_S, cartId, consumerReference));
    }
}
