package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.CategoryEntity;
import com.example.demo.entities.ProductEntity;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        CategoryEntity category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ProductEntity productEntity = mapToEntity(productDTO);
        productEntity.setCategory(category);

        return mapToDTO(productRepository.save(productEntity));
    }



    @Override
    public ProductDTO getProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return mapToDTO(productEntity);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setAvailable(productDTO.getAvailable());
        existingProduct.getCategory().setId(productDTO.getCategoryId());

        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return mapToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(ProductEntity entity) {
        return ProductDTO.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .available(entity.getAvailable())
                .categoryId(entity.getCategory().getId())
                .build();
    }

    private ProductEntity mapToEntity(ProductDTO dto) {
        return ProductEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .available(dto.getAvailable())
                .build();
    }

}
