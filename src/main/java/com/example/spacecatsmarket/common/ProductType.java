package com.example.spacecatsmarket.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    COSMIC_CATNIP("Cosmic Catnip"),
    NEBULA_NAPPING_PODS("Nebula Napping Pods"),
    PLASMA_PAW_WARMERS("Plasma Paw Warmers");

    private final String displayName;


    public static ProductType fromDisplayName(String name) {
        for (ProductType type : values()) {
            if (type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("No enum constant with display name: %s", name));
    }
}
