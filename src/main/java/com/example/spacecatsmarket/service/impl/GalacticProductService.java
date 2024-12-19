package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import com.example.spacecatsmarket.featuretoggle.FeatureToggles;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalacticProductService {

    @FeatureToggle(FeatureToggles.GALACTIC_PRODUCTS)
    public List<String> getGalacticProducts() {
        return List.of("Galactic Rocket", "Cosmic Shield", "Alien Detector");
    }
}
