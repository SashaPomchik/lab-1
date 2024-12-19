package com.example.spacecatsmarket.featuretoggle;

import lombok.Getter;

@Getter
public enum FeatureToggles {

    GALACTIC_PRODUCTS("galactic-products");

    private final String featureName;

    FeatureToggles(String featureName) {
        this.featureName = featureName;
    }
}
