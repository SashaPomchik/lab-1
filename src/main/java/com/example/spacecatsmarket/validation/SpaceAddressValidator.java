package com.example.spacecatsmarket.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class SpaceAddressValidator implements ConstraintValidator<ValidSpaceAddress, String> {

    private static final String SPACE_ADDRESS_PATTERN = "^Sector \\d+, Planet [A-Za-z]+, Quadrant \\d+$";

    private static final Pattern pattern = Pattern.compile(SPACE_ADDRESS_PATTERN);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return pattern.matcher(value).matches();
    }
}
