package com.example.demo.service;

import com.example.demo.exceptions.FeatureNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CosmoCatServiceTest {

    @Autowired
    private CosmoCatService cosmoCatService;

    @Autowired
    private FeatureToggleService featureToggleService;

    @BeforeEach
    void setUp() {
        featureToggleService.setCosmoCatsEnabled(true);
    }

    @Test
    void testGetCosmoCatsEnabled() {
        featureToggleService.setCosmoCatsEnabled(true);
        String result = cosmoCatService.getCosmoCats();
        assertEquals("Here are the cosmic cats!", result);
    }

    @Test
    void testGetCosmoCatsDisabled() {
        featureToggleService.setCosmoCatsEnabled(false);
        assertThrows(FeatureNotAvailableException.class, () -> cosmoCatService.getCosmoCats());
    }
}
