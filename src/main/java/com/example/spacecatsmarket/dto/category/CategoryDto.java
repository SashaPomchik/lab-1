package com.example.spacecatsmarket.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class CategoryDto {

    Long id;

    @NotNull(message = "Category name cannot be null")
    @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters")
    String name;

    @NotNull(message = "Category description cannot be null")
    @Size(min = 10, max = 200, message = "Category description must be between 10 and 200 characters")
    String description;
}
