package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {

    private List<String> cosmicTerms = Arrays.asList("star", "galaxy", "comet", "planet", "cosmic", "space");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        String lowerCaseValue = value.toLowerCase();
        return cosmicTerms.stream().anyMatch(lowerCaseValue::contains);
    }
}