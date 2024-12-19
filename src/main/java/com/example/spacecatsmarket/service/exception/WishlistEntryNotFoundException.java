package com.example.spacecatsmarket.service.exception;

public class WishlistEntryNotFoundException extends RuntimeException {

    private static final String WISHLIST_ENTRY_NOT_FOUND_MESSAGE = "Wishlist entry for customer '%s' and product '%s' not found";

    public WishlistEntryNotFoundException(Long customerId, String productId) {
        super(String.format(WISHLIST_ENTRY_NOT_FOUND_MESSAGE, customerId, productId));
    }
}
