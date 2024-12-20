package com.example.demo;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.CategoryEntity;
import com.example.demo.entities.ProductEntity;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product Controller Integration Tests with Security")
public class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateProductAsAdmin() throws Exception {
        var category = categoryRepository.saveAndFlush(CategoryEntity.builder()
                .name("Test galaxy Category")
                .build());

        var productDTO = ProductDTO.builder()
                .name("Test galaxy Product")
                .description("Test Description")
                .price(100.0)
                .available(true)
                .categoryId(category.getId())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(productDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(productDTO.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productDTO.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(productDTO.getAvailable()));

        assertThat(productRepository.findByName(productDTO.getName())).isPresent();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldFailToCreateProductWithInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetProductByIdAsUser() throws Exception {
        var category = categoryRepository.saveAndFlush(CategoryEntity.builder()
                .name("Test Category")
                .build());

        var product = productRepository.saveAndFlush(ProductEntity.builder()
                .name("Test Product")
                .description("Product Description")
                .price(100.0)
                .available(true)
                .category(category)
                .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(product.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(product.getAvailable()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteProductAsAdmin() throws Exception {
        var category = categoryRepository.saveAndFlush(CategoryEntity.builder()
                .name("Test Category")
                .build());

        var product = productRepository.saveAndFlush(ProductEntity.builder()
                .name("Test Product")
                .description("Product Description")
                .price(100.0)
                .available(true)
                .category(category)
                .build());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertThat(productRepository.findById(product.getId())).isNotPresent();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateProductAsAdmin() throws Exception {
        var category = categoryRepository.saveAndFlush(CategoryEntity.builder()
                .name("Test Category")
                .build());

        var product = productRepository.saveAndFlush(ProductEntity.builder()
                .name("Old galaxy Product")
                .description("Old Description")
                .price(50.0)
                .available(false)
                .category(category)
                .build());

        var updatedProductDTO = ProductDTO.builder()
                .name("Updated galaxy Product")
                .description("Updated Description")
                .price(150.0)
                .available(true)
                .categoryId(category.getId())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedProductDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(updatedProductDTO.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(updatedProductDTO.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(updatedProductDTO.getAvailable()));

        var updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertThat(updatedProduct.getName()).isEqualTo(updatedProductDTO.getName());
        assertThat(updatedProduct.getDescription()).isEqualTo(updatedProductDTO.getDescription());
        assertThat(updatedProduct.getPrice()).isEqualTo(updatedProductDTO.getPrice());
        assertThat(updatedProduct.getAvailable()).isEqualTo(updatedProductDTO.getAvailable());
    }
}
