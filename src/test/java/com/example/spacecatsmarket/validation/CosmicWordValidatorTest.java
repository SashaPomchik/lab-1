package com.example.spacecatsmarket.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CosmicWordValidatorTest {

    private final CosmicWordValidator validator = new CosmicWordValidator();

    @Test
    void testValidCosmicWord() {
        assertTrue(validator.isValid("This is a galaxy product", null));
        assertTrue(validator.isValid("Amazing star product", null));
        assertTrue(validator.isValid("Comet-inspired design", null));
    }

    @Test
    void testInvalidCosmicWord() {
        assertFalse(validator.isValid("This is a regular product", null));
        assertFalse(validator.isValid("Simple description", null));
        assertFalse(validator.isValid("Hello world!", null));
    }

    @Test
    void testNullOrEmptyValue() {
        assertFalse(validator.isValid(null, null));
        assertFalse(validator.isValid("", null));
        assertFalse(validator.isValid("   ", null));
    }
}
