package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.AbstractIt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@DisplayName("Greeting Controller IT")
@Tag("greeting-service")
class GreetingControllerIT extends AbstractIt {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetGreetingByName() throws Exception {
        mockMvc.perform(get("/api/v1/greetings/{name}", "dobby"))
                .andExpect(status().isOk())
                .andExpect(content().string("Salut from space cat dobby"));
    }

    @Test
    void shouldThrowCatNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/greetings/{name}", "unknownCat"))
                .andExpect(status().isNotFound());
    }
}
