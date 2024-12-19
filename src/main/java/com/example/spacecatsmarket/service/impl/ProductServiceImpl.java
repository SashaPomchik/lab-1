package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.product.Product;
import com.example.spacecatsmarket.service.interfaces.ProductService;
import com.example.spacecatsmarket.service.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final Map<String, Product> productRepository = new HashMap<>();

    @Override
    public Product createProduct(Product product) {
        productRepository.put(product.getId(), product);
        log.info("Product created with ID: {}", product.getId());
        return product;
    }

    @Override
    public Optional<Product> getProductById(String productId) {
        return Optional.ofNullable(Optional.ofNullable(productRepository.get(productId))
                .orElseThrow(() -> new ProductNotFoundException(productId)));
    }

    @Override
    public boolean existsById(String productId) {
        return productRepository.containsKey(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.values());
    }

    @Override
    public Product updateProduct(String productId, Product updatedProduct) {
        if (!productRepository.containsKey(productId)) {
            throw new ProductNotFoundException(productId);
        }
        productRepository.put(productId, updatedProduct);
        log.info("Product updated with ID: {}", productId);
        return updatedProduct;
    }

    @Override
    public void deleteProduct(String productId) {
        if (!productRepository.containsKey(productId)) {
            throw new ProductNotFoundException(productId);
        }
        productRepository.remove(productId);
        log.info("Product deleted with ID: {}", productId);
    }

    private Product createProductMock(String name, String description, Double price, String category, int stock) {
        return Product.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .description(description)
                .price(price)
                .category(category)
                .stock(stock)
                .build();
    }
}
