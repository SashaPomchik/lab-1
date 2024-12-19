package com.example.demo.repositories;

import com.example.demo.entities.ProductEntity;
import com.example.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {}

