package com.example.demo;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.CategoryEntity;
import com.example.demo.entities.ProductEntity;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    private ProductServiceImpl productService;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        productService = new ProductServiceImpl(productRepository, categoryRepository);
    }

    @Test
    public void testCreateProduct() {
        ProductDTO productDTO = ProductDTO.builder()
                .name("Cosmic Yarn")
                .description("Anti-gravity yarn ball")
                .price(15.99)
                .available(true)
                .categoryId(1L)
                .build();

        CategoryEntity category = new CategoryEntity();
        category.setId(1L);

        ProductEntity savedProduct = ProductEntity.builder()
                .id(1L)
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .available(productDTO.getAvailable())
                .category(category)
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(savedProduct);

        ProductDTO createdProduct = productService.createProduct(productDTO);

        assertEquals("Cosmic Yarn", createdProduct.getName());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    public void testGetProductById_ProductExists() {
        CategoryEntity category = new CategoryEntity();
        category.setId(1L);

        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .name("Galactic Milk")
                .description("Milk from space cows")
                .price(9.99)
                .available(true)
                .category(category)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        ProductDTO fetchedProduct = productService.getProductById(1L);

        assertNotNull(fetchedProduct);
        assertEquals("Galactic Milk", fetchedProduct.getName());
    }

    @Test
    public void testGetProductById_ProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductById(999L));
        assertEquals("Product not found with id: 999", exception.getMessage());
    }

    @Test
    public void testGetAllProducts() {
        CategoryEntity category = new CategoryEntity();
        category.setId(1L);

        ProductEntity product1 = ProductEntity.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(10.0)
                .available(true)
                .category(category)
                .build();

        ProductEntity product2 = ProductEntity.builder()
                .id(2L)
                .name("Product 2")
                .description("Description 2")
                .price(20.0)
                .available(true)
                .category(category)
                .build();

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<ProductDTO> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertEquals("Product 1", products.get(0).getName());
        assertEquals("Product 2", products.get(1).getName());
    }

    @Test
    public void testUpdateProduct_ProductExists() {
        CategoryEntity category = new CategoryEntity();
        category.setId(1L);

        ProductEntity existingProduct = ProductEntity.builder()
                .id(1L)
                .name("Old Name")
                .description("Old Description")
                .price(10.0)
                .available(true)
                .category(category)
                .build();

        ProductDTO updateDTO = ProductDTO.builder()
                .name("New Name")
                .description("New Description")
                .price(20.0)
                .available(false)
                .categoryId(1L)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(existingProduct);

        ProductDTO updatedProduct = productService.updateProduct(1L, updateDTO);

        assertEquals("New Name", updatedProduct.getName());
        assertEquals("New Description", updatedProduct.getDescription());
        assertEquals(20.0, updatedProduct.getPrice());
        assertFalse(updatedProduct.getAvailable());
    }

    @Test
    public void testUpdateProduct_ProductNotFound() {
        ProductDTO updateDTO = ProductDTO.builder()
                .name("New Name")
                .description("New Description")
                .price(20.0)
                .available(false)
                .categoryId(1L)
                .build();

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> productService.updateProduct(999L, updateDTO));

        assertEquals("Product not found with id: 999", exception.getMessage());
    }

    @Test
    public void testDeleteProduct_ProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> productService.deleteProduct(999L));

        assertEquals("Product not found with id: 999", exception.getMessage());
    }
}
