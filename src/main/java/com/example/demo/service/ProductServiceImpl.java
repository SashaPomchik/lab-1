package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private List<ProductDTO> products = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        productDTO.setId(nextId++);
        products.add(productDTO);
        return productDTO;
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return products;
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        ProductDTO existingProduct = getProductById(id);
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setAvailable(productDTO.getAvailable());
        existingProduct.setCategoryId(productDTO.getCategoryId());
        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        ProductDTO product = getProductById(id);
        products.remove(product);
    }
}