package com.example.spacecatsmarket.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class OrderEntryDto {

    @NotNull(message = "ProductMapper name cannot be null")
    String productType;

    @NotNull(message = "Quantity cannot be null")
    int amount;
}
