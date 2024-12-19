package com.example.spacecatsmarket.web.controller;

import com.example.spacecatsmarket.service.impl.GalacticProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/galactic-products")
@RequiredArgsConstructor
public class GalacticProductController {

    private final GalacticProductService galacticProductService;

    @GetMapping
    public ResponseEntity<List<String>> getGalacticProducts() {
        return ResponseEntity.ok(galacticProductService.getGalacticProducts());
    }
}
