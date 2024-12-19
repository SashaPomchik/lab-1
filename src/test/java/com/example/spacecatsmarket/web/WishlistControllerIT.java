package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.AbstractIt;
import com.example.spacecatsmarket.dto.whishlist.WishlistEntryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@DisplayName("Wishlist Controller IT")
@Tag("wishlist-service")
class WishlistControllerIT extends AbstractIt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final WishlistEntryDto TEST_WISHLIST_ENTRY = WishlistEntryDto.builder()
            .productId("12345")
            .notifiedWhenAvailable(true)
            .build();

    @Test
    void shouldAddToWishlist() throws Exception {
        mockMvc.perform(post("/api/v1/{customerId}/wishlist", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_WISHLIST_ENTRY)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetWishlist() throws Exception {
        mockMvc.perform(get("/api/v1/{customerId}/wishlist", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    @Test
    void shouldRemoveFromWishlist() throws Exception {
        mockMvc.perform(delete("/api/v1/{customerId}/wishlist/{productId}", 1L, "12345"))
                .andExpect(status().isNoContent());
    }
}
