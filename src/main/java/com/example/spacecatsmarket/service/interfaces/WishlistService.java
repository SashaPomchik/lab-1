package com.example.spacecatsmarket.service.interfaces;

import com.example.spacecatsmarket.domain.wishlist.WishlistEntry;

import java.util.List;

public interface WishlistService {

    void addToWishlist(Long customerId, WishlistEntry wishlistEntry);

    void removeFromWishlist(Long customerId, String productId);

    List<WishlistEntry> getWishlist(Long customerId);
}
