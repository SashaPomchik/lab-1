package com.example.spacecatsmarket.web.controller;

import com.example.spacecatsmarket.domain.product.Product;
import com.example.spacecatsmarket.dto.product.ProductDto;
import com.example.spacecatsmarket.service.interfaces.ProductService;
import com.example.spacecatsmarket.web.mapper.ProductMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(productMapper.toProductDto(createdProduct));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return ResponseEntity.ok(productMapper.toProductDto(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts().stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable String productId,
            @RequestBody @Valid ProductDto productDto) {
        Product updatedProduct = productMapper.toProduct(productDto);
        Product product = productService.updateProduct(productId, updatedProduct);
        return ResponseEntity.ok(productMapper.toProductDto(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        if (productService.existsById(productId)) {
            productService.deleteProduct(productId);
        }
        return ResponseEntity.noContent().build();
    }
}
