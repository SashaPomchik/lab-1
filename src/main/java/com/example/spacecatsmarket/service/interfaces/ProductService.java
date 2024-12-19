package com.example.spacecatsmarket.service.interfaces;

import com.example.spacecatsmarket.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product product);

    Optional<Product> getProductById(String productId);

    boolean existsById(String productId);

    List<Product> getAllProducts();

    Product updateProduct(String productId, Product updatedProduct);

    void deleteProduct(String productId);
}
