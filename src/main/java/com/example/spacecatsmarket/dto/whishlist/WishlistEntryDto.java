package com.example.spacecatsmarket.dto.whishlist;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class WishlistEntryDto {

    @NotNull(message = "Product ID cannot be null")
    String productId;

    @NotNull(message = "Notified flag cannot be null")
    boolean notifiedWhenAvailable;
}
