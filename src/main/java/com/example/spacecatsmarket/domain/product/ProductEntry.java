package com.example.spacecatsmarket.domain.product;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductEntry {

    String productId;
    int quantity;
}
