package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.wishlist.WishlistEntry;
import com.example.spacecatsmarket.service.exception.WishlistEntryNotFoundException;
import com.example.spacecatsmarket.service.impl.WishlistServiceImpl;
import com.example.spacecatsmarket.service.interfaces.WishlistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = WishlistServiceImpl.class)
@DisplayName("Wishlist Service Tests")
class WishlistServiceTest {

    @Autowired
    private WishlistService wishlistService;

    private static final Long CUSTOMER_ID = 1L;
    private static final WishlistEntry DEFAULT_ENTRY = WishlistEntry.builder()
            .customerId(CUSTOMER_ID)
            .productId("product-123")
            .notifiedWhenAvailable(true)
            .build();

    @Test
    void shouldAddToWishlist() {
        wishlistService.addToWishlist(CUSTOMER_ID, DEFAULT_ENTRY);

        List<WishlistEntry> wishlist = wishlistService.getWishlist(CUSTOMER_ID);

        assertEquals(1, wishlist.size());
        assertEquals(DEFAULT_ENTRY, wishlist.get(0));
    }

    @Test
    void shouldRemoveFromWishlist() {
        wishlistService.addToWishlist(CUSTOMER_ID, DEFAULT_ENTRY);
        wishlistService.removeFromWishlist(CUSTOMER_ID, DEFAULT_ENTRY.getProductId());

        List<WishlistEntry> wishlist = wishlistService.getWishlist(CUSTOMER_ID);

        assertTrue(wishlist.isEmpty());
    }

    @Test
    void shouldNotThrowExceptionWhenRemovingNonExistentProduct() {
        Long customerId = 1L;
        String nonExistentProductId = "99999";

        assertDoesNotThrow(() -> wishlistService.removeFromWishlist(customerId, nonExistentProductId));
    }


    @Test
    void shouldReturnEmptyWishlistWhenNoEntriesExist() {
        List<WishlistEntry> wishlist = wishlistService.getWishlist(CUSTOMER_ID);

        assertTrue(wishlist.isEmpty());
    }
}
