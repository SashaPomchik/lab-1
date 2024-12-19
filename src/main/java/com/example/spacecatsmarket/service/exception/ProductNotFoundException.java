package com.example.spacecatsmarket.service.exception;

public class ProductNotFoundException extends RuntimeException {

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with ID '%s' not found";

    public ProductNotFoundException(String productId) {
        super(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId));
    }
}
