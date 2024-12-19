package com.example.demo;

import com.example.demo.controller.ProductController;
import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateProduct_ValidData() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Cosmic Yarn");
        productDTO.setDescription("Anti-gravity yarn ball");
        productDTO.setPrice(15.99);
        productDTO.setAvailable(true);
        productDTO.setCategoryId(1L);

        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Cosmic Yarn"));
    }

    @Test
    public void testCreateProduct_InvalidData() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("");
        productDTO.setPrice(-5.0);
        productDTO.setAvailable(true);
        productDTO.setCategoryId(1L);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").exists()); // Зміна $.message на $.detail
    }

    @Test
    public void testGetProductById_ProductExists() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Cosmic Yarn");

        when(productService.getProductById(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/api/v1/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Cosmic Yarn"));
    }

    @Test
    public void testUpdateProduct_InvalidData() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("");
        productDTO.setPrice(-10.0);
        productDTO.setAvailable(true);
        productDTO.setCategoryId(1L);

        mockMvc.perform(put("/api/v1/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").exists()); // Зміна $.message на $.detail
    }

    @Test
    public void testCreateProduct_MissingRequiredFields() throws Exception {
        ProductDTO productDTO = new ProductDTO();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").exists()); // Зміна $.message на $.detail
    }

    @Test
    public void testGetAllProducts() throws Exception {
        ProductDTO product1 = new ProductDTO();
        product1.setId(1L);
        product1.setName("Cosmic Yarn");
        product1.setPrice(15.99);
        product1.setAvailable(true);
        product1.setCategoryId(1L);

        ProductDTO product2 = new ProductDTO();
        product2.setId(2L);
        product2.setName("Galactic Milk");
        product2.setPrice(9.99);
        product2.setAvailable(true);
        product2.setCategoryId(2L);

        when(productService.getAllProducts()).thenReturn(List.of(product1, product2));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cosmic Yarn"))
                .andExpect(jsonPath("$[1].name").value("Galactic Milk"));
    }
}
