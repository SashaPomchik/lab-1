package com.example.demo.dto;

import com.example.demo.validation.CosmicWordCheck;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    Long id;

    @CosmicWordCheck
    @NotBlank(message = "Name must not be blank")
    @Size(max = 255, message = "Name must be less than 255 characters")
    String name;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    String description;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    Double price;

    @NotNull(message = "Availability must not be null")
    Boolean available;

    @NotNull(message = "Category ID must not be null")
    Long categoryId;
}
