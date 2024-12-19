package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.category.Category;
import com.example.spacecatsmarket.service.interfaces.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> mockCategories = new ArrayList<>();

    @Override
    public Category create(Category category) {
        category = Category.builder()
                .id((long) (mockCategories.size() + 1))
                .name(category.getName())
                .description(category.getDescription())
                .build();
        mockCategories.add(category);
        log.info("Category created: {}", category);
        return category;
    }

    @Override
    public Category getById(Long id) {
        return mockCategories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public List<Category> getAll() {
        return new ArrayList<>(mockCategories);
    }

    @Override
    public Category update(Long id, Category updatedCategory) {
        Optional<Category> existingCategory = mockCategories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();

        if (existingCategory.isEmpty()) {
            throw new RuntimeException("Category not found with id: " + id);
        }

        Category category = Category.builder()
                .id(id)
                .name(updatedCategory.getName())
                .description(updatedCategory.getDescription())
                .build();

        mockCategories.remove(existingCategory.get());
        mockCategories.add(category);

        log.info("Category updated: {}", category);
        return category;
    }

    @Override
    public void delete(Long id) {
        boolean removed = mockCategories.removeIf(category -> category.getId().equals(id));
        if (!removed) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        log.info("Category deleted with id: {}", id);
    }
}
