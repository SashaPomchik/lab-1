package com.example.spacecatsmarket.validation;

import com.example.spacecatsmarket.validation.annotation.CosmicWordCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {

    private static final List<String> COSMIC_TERMS = Arrays.asList("star", "galaxy", "comet", "planet", "cosmos");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        return COSMIC_TERMS.stream().anyMatch(value.toLowerCase()::contains);
    }
}
