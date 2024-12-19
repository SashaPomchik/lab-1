package com.example.demo.repositories;

import com.example.demo.entities.CategoryEntity;
import com.example.demo.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {}

