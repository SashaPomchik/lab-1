package com.example.demo.repositories;

import com.example.demo.entities.CategoryEntity;
import com.example.demo.entities.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class ProductRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldSaveAndRetrieveProduct() {
        CategoryEntity category = CategoryEntity.builder()
                .name("Books")
                .build();

        category = categoryRepository.save(category);

        ProductEntity product = ProductEntity.builder()
                .name("Java Programming")
                .description("A comprehensive guide to Java.")
                .price(45.99)
                .available(true)
                .category(category)
                .build();

        product = productRepository.save(product);

        assertThat(productRepository.findById(product.getId())).isPresent();
    }

    @Test
    void shouldUpdateProduct() {
        CategoryEntity category = CategoryEntity.builder()
                .name("Books")
                .build();
        category = categoryRepository.save(category);

        ProductEntity product = ProductEntity.builder()
                .name("Java Programming")
                .description("A comprehensive guide to Java.")
                .price(45.99)
                .available(true)
                .category(category)
                .build();

        product = productRepository.save(product);

        ProductEntity updatedProduct = product.toBuilder()
                .name("Advanced Java Programming")
                .build();

        updatedProduct = productRepository.save(updatedProduct);

        Optional<ProductEntity> retrievedProduct = productRepository.findById(updatedProduct.getId());
        assertThat(retrievedProduct).isPresent();
        assertThat(retrievedProduct.get().getName()).isEqualTo("Advanced Java Programming");
    }

    @Test
    void shouldDeleteProduct() {
        CategoryEntity category = CategoryEntity.builder()
                .name("Books")
                .build();
        category = categoryRepository.save(category);

        ProductEntity product = ProductEntity.builder()
                .name("Java Programming")
                .description("A comprehensive guide to Java.")
                .price(45.99)
                .available(true)
                .category(category)
                .build();

        product = productRepository.save(product);
        Long productId = product.getId();

        productRepository.delete(product);

        Optional<ProductEntity> deletedProduct = productRepository.findById(productId);
        assertThat(deletedProduct).isNotPresent();
    }

    @Test
    void shouldNotSaveProductWithoutCategory() {
        // Создаем категорию, но не связываем её с продуктом
        CategoryEntity category = CategoryEntity.builder()
                .name("Programming")
                .build();
        categoryRepository.save(category);

        ProductEntity product = ProductEntity.builder()
                .name("Java Programming")
                .description("A comprehensive guide to Java.")
                .price(45.99)
                .available(true)
                .category(category)
                .build();

        ProductEntity savedProduct = productRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull();
    }
}
