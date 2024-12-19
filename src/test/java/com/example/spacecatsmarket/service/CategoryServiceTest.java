package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.category.Category;
import com.example.spacecatsmarket.service.impl.CategoryServiceImpl;
import com.example.spacecatsmarket.service.interfaces.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CategoryServiceImpl.class)
@DisplayName("Category Service Tests")
class CategoryServiceTest {


    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void resetCategories() {
        categoryService.getAll().clear();
    }

    private static final Category DEFAULT_CATEGORY = Category.builder()
            .name("Cosmic Accessories")
            .description("Category for all cosmic accessories.")
            .build();

    @Test
    void shouldCreateCategory() {
        Category createdCategory = categoryService.create(DEFAULT_CATEGORY);

        assertNotNull(createdCategory);
        assertEquals(DEFAULT_CATEGORY.getName(), createdCategory.getName());
    }


    @Test
    void shouldGetCategoryById() {
        Category createdCategory = categoryService.create(DEFAULT_CATEGORY);

        Category retrievedCategory = categoryService.getById(createdCategory.getId());

        assertNotNull(retrievedCategory);
        assertEquals(DEFAULT_CATEGORY.getName(), retrievedCategory.getName());
    }

    @Test
    void shouldThrowExceptionIfCategoryNotFound() {
        assertThrows(RuntimeException.class, () -> categoryService.getById(99L));
    }

    @Test
    void shouldUpdateCategory() {
        Category createdCategory = categoryService.create(DEFAULT_CATEGORY);

        Category updatedCategory = Category.builder()
                .id(createdCategory.getId())
                .name("Updated Cosmic Accessories")
                .description("Updated description.")
                .build();

        Category result = categoryService.update(createdCategory.getId(), updatedCategory);

        assertEquals("Updated Cosmic Accessories", result.getName());
    }

    @Test
    void shouldDeleteCategory() {
        Category createdCategory = categoryService.create(DEFAULT_CATEGORY);

        categoryService.delete(createdCategory.getId());

        assertThrows(RuntimeException.class, () -> categoryService.getById(createdCategory.getId()));
    }
}
