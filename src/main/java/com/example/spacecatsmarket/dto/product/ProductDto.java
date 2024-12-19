package com.example.spacecatsmarket.dto.product;

import com.example.spacecatsmarket.validation.ExtendedValidation;
import com.example.spacecatsmarket.validation.annotation.CosmicWordCheck;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ProductDto {

    @NotNull(message = "Product ID cannot be null")
    String id;

    @NotNull(message = "Product name cannot be null")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @CosmicWordCheck(groups = ExtendedValidation.class)
    String name;

    @NotNull(message = "Description cannot be null")
    @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
    String description;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    Double price;

    @NotNull(message = "Stock cannot be null")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    int stock;

    @NotNull(message = "Category cannot be null")
    @Size(min = 2, max = 50, message = "Category must be between 2 and 50 characters")
    String category;
}
