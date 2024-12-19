package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.AbstractIt;
import com.example.spacecatsmarket.featuretoggle.FeatureToggleExtension;
import com.example.spacecatsmarket.featuretoggle.FeatureToggles;
import com.example.spacecatsmarket.featuretoggle.annotation.DisabledFeatureToggle;
import com.example.spacecatsmarket.featuretoggle.annotation.EnabledFeatureToggle;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DisplayName("Galactic Product Controller IT")
@ExtendWith(FeatureToggleExtension.class)
class GalacticProductControllerIT extends AbstractIt {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @DisabledFeatureToggle(FeatureToggles.GALACTIC_PRODUCTS)
    void shouldGet404WhenFeatureDisabled() {
        mockMvc.perform(get("/api/v1/galactic-products"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    @EnabledFeatureToggle(FeatureToggles.GALACTIC_PRODUCTS)
    void shouldGet200WhenFeatureEnabled() {
        mockMvc.perform(get("/api/v1/galactic-products"))
                .andExpect(status().isOk());
    }
}
