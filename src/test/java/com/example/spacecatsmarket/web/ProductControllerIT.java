package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.AbstractIt;
import com.example.spacecatsmarket.domain.product.Product;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.service.interfaces.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@DisplayName("Product Controller IT")
@Tag("product-service")
class ProductControllerIT extends AbstractIt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    private final ProductDto TEST_PRODUCT = ProductDto.builder()
            .id("1")
            .name("Test Product")
            .description("Test Product Description")
            .price(100.0)
            .category("Test Category")
            .build();

    private final ProductDto UPDATED_TEST_PRODUCT = ProductDto.builder()
            .id("12345")
            .name("Test Product")
            .description("Test Product Description")
            .price(100.0)
            .category("Updated Category")
            .build();

    @BeforeEach
    void setupProducts() {
        addTestProduct(TEST_PRODUCT);
        addTestProduct(UPDATED_TEST_PRODUCT);
    }

    private void addTestProduct(ProductDto productDto) {
        productService.createProduct(Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .category(productDto.getCategory())
                .stock(100)
                .build());
    }

    @Test
    void shouldCreateProduct() throws Exception {
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_PRODUCT)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(TEST_PRODUCT.getName()));
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    @Test
    void shouldGetProductById() throws Exception {
        mockMvc.perform(get("/api/v1/products/{productId}", "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("12345"));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        mockMvc.perform(put("/api/v1/products/{productId}", "12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATED_TEST_PRODUCT)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(UPDATED_TEST_PRODUCT.getName()));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/products/{productId}", "12345"))
                .andExpect(status().isNoContent());
    }
}
