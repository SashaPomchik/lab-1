package com.example.demo.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class FeatureToggleService {

    @Value("${feature.cosmoCats.enabled}")
    private boolean cosmoCatsEnabled;

    @Value("${feature.kittyProducts.enabled}")
    private boolean kittyProductsEnabled;

}
