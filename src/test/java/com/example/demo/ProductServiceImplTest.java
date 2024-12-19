package com.example.demo;

import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        productService = new ProductServiceImpl();
    }

    @Test
    public void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Cosmic Yarn");
        productDTO.setDescription("Anti-gravity yarn ball");
        productDTO.setPrice(15.99);
        productDTO.setAvailable(true);
        productDTO.setCategoryId(1L);

        ProductDTO createdProduct = productService.createProduct(productDTO);

        assertNotNull(createdProduct.getId());
        assertEquals("Cosmic Yarn", createdProduct.getName());
        assertEquals(1, productService.getAllProducts().size());
    }

    @Test
    public void testGetProductById_ProductExists() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Galactic Milk");
        productDTO.setPrice(9.99);
        productDTO.setAvailable(true);
        productDTO.setCategoryId(2L);

        ProductDTO createdProduct = productService.createProduct(productDTO);

        ProductDTO fetchedProduct = productService.getProductById(createdProduct.getId());

        assertEquals(createdProduct.getId(), fetchedProduct.getId());
        assertEquals("Galactic Milk", fetchedProduct.getName());
    }

    @Test
    public void testGetAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        assertEquals(0, products.size());

        ProductDTO product1 = new ProductDTO();

        product1.setId(1L);
        product1.setName("Product 1");
        product1.setDescription("Product 1");
        product1.setPrice(10.0);
        product1.setAvailable(true);
        product1.setCategoryId(1L);

        ProductDTO product2 = new ProductDTO();

        product2.setId(2L);
        product2.setName("Product 2");
        product2.setDescription("Product 2");
        product2.setPrice(10.0);
        product2.setAvailable(true);
        product2.setCategoryId(1L);

        productService.createProduct(product1);
        productService.createProduct(product2);

        products = productService.getAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    public void testUpdateProduct_ProductExists() {
        // Створимо продукт
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(1L);
        productDTO.setName("Product 1");
        productDTO.setDescription("Product 1");
        productDTO.setPrice(10.0);
        productDTO.setAvailable(true);
        productDTO.setCategoryId(1L);
        ProductDTO createdProduct = productService.createProduct(productDTO);

        ProductDTO updateDTO = new ProductDTO();

        updateDTO.setId(2L);
        updateDTO.setName("New Name");
        updateDTO.setDescription("New Description");
        updateDTO.setPrice(10.0);
        updateDTO.setAvailable(true);
        updateDTO.setCategoryId(2L);

        ProductDTO updatedProduct = productService.updateProduct(createdProduct.getId(), updateDTO);

        assertEquals("New Name", updatedProduct.getName());
        assertEquals("New Description", updatedProduct.getDescription());
        assertEquals(10.0, updatedProduct.getPrice());
        assertTrue(updatedProduct.getAvailable());
        assertEquals(2L, updatedProduct.getCategoryId());
    }

    @Test
    public void testUpdateProduct_ProductNotFound() {
        ProductDTO updateDTO = new ProductDTO();

        updateDTO.setId(1L);
        updateDTO.setName("New Name");
        updateDTO.setDescription("New Description");
        updateDTO.setPrice(10.0);
        updateDTO.setAvailable(true);
        updateDTO.setCategoryId(1L);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(999L, updateDTO);
        });

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    public void testDeleteProduct_ProductExists() {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(1L);
        productDTO.setName("Product 1");
        productDTO.setDescription("Product 1");
        productDTO.setPrice(10.0);
        productDTO.setAvailable(true);
        productDTO.setCategoryId(1L);

        ProductDTO createdProduct = productService.createProduct(productDTO);

        productService.deleteProduct(createdProduct.getId());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(createdProduct.getId());
        });

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    public void testDeleteProduct_ProductNotFound() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(999L);
        });

        assertEquals("Product not found", exception.getMessage());
    }
}

