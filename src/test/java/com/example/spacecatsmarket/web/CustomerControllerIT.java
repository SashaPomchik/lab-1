package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.AbstractIt;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@DisplayName("Customer Controller IT")
@Tag("customer-service")
class CustomerControllerIT extends AbstractIt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final CustomerDetailsDto TEST_CUSTOMER = CustomerDetailsDto.builder()
            .name("Test Customer")
            .address("Sector 5, Planet Zeta, Quadrant 12")
            .phoneNumber("123-456-7890")
            .email("test@example.com")
            .preferredChannel(List.of(new String[]{"email"}))
            .build();

    @Test
    @DisplayName("Should fetch all customers successfully")
    void shouldGetAllCustomers() throws Exception {
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerDetailsEntries").isArray())
                .andExpect(jsonPath("$.customerDetailsEntries.length()").value(3));
    }

    @Test
    @DisplayName("Should fetch a single customer by ID successfully")
    void shouldGetCustomerById() throws Exception {
        mockMvc.perform(get("/api/v1/customers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice Johnson"))
                .andExpect(jsonPath("$.address").value("123 Cosmic Lane, Catnip City"))
                .andExpect(jsonPath("$.phoneNumber").value("123-456-7890"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    @DisplayName("Should create a new customer successfully")
    void shouldCreateCustomer() throws Exception {
        String customerJson = objectMapper.writeValueAsString(TEST_CUSTOMER);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Customer"))
                .andExpect(jsonPath("$.address").value("Sector 5, Planet Zeta, Quadrant 12"));
    }

    @Test
    @DisplayName("Should handle validation error when creating a customer")
    void shouldFailToCreateCustomerWithInvalidData() throws Exception {
        String invalidCustomerJson = """
        {
            "name": "T",
            "address": "",
            "phoneNumber": "123",
            "email": "invalid-email"
        }
        """;

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCustomerJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Field Validation Exception"))
                .andExpect(jsonPath("$.invalidParams").isArray());
    }
}
