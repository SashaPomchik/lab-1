package com.example.spacecatsmarket.service.interfaces;


import com.example.spacecatsmarket.domain.category.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    Category getById(Long id);
    List<Category> getAll();
    Category update(Long id, Category category);
    void delete(Long id);
}
