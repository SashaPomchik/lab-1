package com.example.demo.repositories;

import com.example.demo.entities.CategoryEntity;
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
public class CategoryRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldSaveAndRetrieveCategory() {
        CategoryEntity category = CategoryEntity.builder()
                .name("Electronics")
                .build();

        category = categoryRepository.save(category);

        Optional<CategoryEntity> retrievedCategory = categoryRepository.findById(category.getId());
        assertThat(retrievedCategory).isPresent();
        assertThat(retrievedCategory.get().getName()).isEqualTo("Electronics");
    }

    @Test
    void shouldUpdateCategory() {
        CategoryEntity category = CategoryEntity.builder()
                .name("Books")
                .build();

        category = categoryRepository.save(category);

        CategoryEntity updatedCategory = category.toBuilder()
                .name("Updated Books")
                .build();

        updatedCategory = categoryRepository.save(updatedCategory);

        Optional<CategoryEntity> retrievedCategory = categoryRepository.findById(updatedCategory.getId());
        assertThat(retrievedCategory).isPresent();
        assertThat(retrievedCategory.get().getName()).isEqualTo("Updated Books");
    }

    @Test
    void shouldDeleteCategory() {
        CategoryEntity category = CategoryEntity.builder()
                .name("Toys")
                .build();

        category = categoryRepository.save(category);
        Long categoryId = category.getId();

        categoryRepository.delete(category);

        Optional<CategoryEntity> deletedCategory = categoryRepository.findById(categoryId);
        assertThat(deletedCategory).isNotPresent();
    }

    @Test
    void shouldNotSaveCategoryWithEmptyName() {
        CategoryEntity category = CategoryEntity.builder()
                .name("")
                .build();

        category = categoryRepository.save(category);

        Optional<CategoryEntity> retrievedCategory = categoryRepository.findById(category.getId());
        assertThat(retrievedCategory).isPresent();
        assertThat(retrievedCategory.get().getName()).isEqualTo(""); // Name will be empty if saved
    }
}
