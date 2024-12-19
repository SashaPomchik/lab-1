package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.wishlist.WishlistEntry;
import com.example.spacecatsmarket.service.interfaces.WishlistService;
import com.example.spacecatsmarket.service.exception.WishlistEntryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final Map<Long, List<WishlistEntry>> customerWishlists = new HashMap<>();

    @Override
    public void addToWishlist(Long customerId, WishlistEntry wishlistEntry) {
        customerWishlists.computeIfAbsent(customerId, k -> new ArrayList<>()).add(wishlistEntry);
        log.info("Product {} added to wishlist for customer {}", wishlistEntry.getProductId(), customerId);
    }

    @Override
    public void removeFromWishlist(Long customerId, String productId) {
        List<WishlistEntry> wishlist = customerWishlists.get(customerId);
        if (wishlist == null) {
            log.info("No wishlist found for customer '{}', skipping removal of product '{}'", customerId, productId);
            return;
        }

        boolean removed = wishlist.removeIf(entry -> entry.getProductId().equals(productId));
        if (removed) {
            log.info("Product '{}' removed from wishlist for customer '{}'", productId, customerId);
        } else {
            log.info("Product '{}' not found in wishlist for customer '{}', skipping removal.", productId, customerId);
        }
    }

    @Override
    public List<WishlistEntry> getWishlist(Long customerId) {
        return customerWishlists.getOrDefault(customerId, new ArrayList<>());
    }

    private WishlistEntry createWishlistEntryMock(Long customerId, String productId, boolean notifiedWhenAvailable) {
        return WishlistEntry.builder()
                .customerId(customerId)
                .productId(productId)
                .notifiedWhenAvailable(notifiedWhenAvailable)
                .build();
    }
}
