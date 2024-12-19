package com.example.spacecatsmarket.domain.wishlist;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WishlistEntry {

    Long customerId;
    String productId;
    boolean notifiedWhenAvailable;
}
