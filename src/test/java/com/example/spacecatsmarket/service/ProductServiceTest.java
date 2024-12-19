package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.product.Product;
import com.example.spacecatsmarket.service.exception.ProductNotFoundException;
import com.example.spacecatsmarket.service.impl.ProductServiceImpl;
import com.example.spacecatsmarket.service.interfaces.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ProductServiceImpl.class})
@DisplayName("Product Service Tests")
@TestMethodOrder(OrderAnnotation.class)
class ProductServiceTest {

    private static final String PRODUCT_ID = "test-product-id";
    private static final Product DEFAULT_PRODUCT = buildProduct(PRODUCT_ID);

    @Autowired
    private ProductService productService;

    @Test
    @Order(1)
    void shouldCreateProduct() {
        Product result = productService.createProduct(DEFAULT_PRODUCT);

        assertNotNull(result);
        assertEquals(DEFAULT_PRODUCT.getId(), result.getId());
        assertEquals(DEFAULT_PRODUCT.getName(), result.getName());
        assertEquals(DEFAULT_PRODUCT.getPrice(), result.getPrice());
        assertEquals(DEFAULT_PRODUCT.getCategory(), result.getCategory());
    }

    @Test
    @Order(2)
    void shouldGetProductById() {
        productService.createProduct(DEFAULT_PRODUCT);

        Optional<Product> result = productService.getProductById(PRODUCT_ID);

        assertTrue(result.isPresent());
        assertEquals(DEFAULT_PRODUCT.getId(), result.get().getId());
    }

    @Test
    @Order(3)
    void shouldGetAllProducts() {
        productService.createProduct(DEFAULT_PRODUCT);

        List<Product> products = productService.getAllProducts();

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals(DEFAULT_PRODUCT.getId(), products.get(0).getId());
    }

    @Test
    @Order(4)
    void shouldUpdateProduct() {
        productService.createProduct(DEFAULT_PRODUCT);

        Product updatedProduct = Product.builder()
                .id(PRODUCT_ID)
                .name("Updated Product")
                .description("Updated Description")
                .price(120.0)
                .category("Updated Category")
                .stock(50)
                .build();

        Product result = productService.updateProduct(PRODUCT_ID, updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getDescription(), result.getDescription());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
    }

    @Test
    @Order(5)
    void shouldDeleteProduct() {
        productService.createProduct(DEFAULT_PRODUCT);

        productService.deleteProduct(PRODUCT_ID);

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(PRODUCT_ID));
    }

    @Test
    @Order(6)
    void shouldThrowExceptionWhenProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById("non-existing-id"));
    }

    private static Product buildProduct(String productId) {
        return Product.builder()
                .id(productId)
                .name("Test Product")
                .description("Test Description")
                .price(100.0)
                .category("Test Category")
                .stock(100)
                .build();
    }
}
