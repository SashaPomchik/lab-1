package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.customer.CustomerDetails;
import com.example.spacecatsmarket.service.exception.CustomerNotFoundException;
import com.example.spacecatsmarket.service.impl.CustomerServiceImpl;
import com.example.spacecatsmarket.service.interfaces.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CustomerServiceImpl.class)
@DisplayName("Customer Service Tests")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void shouldGetAllCustomerDetails() {
        List<CustomerDetails> customers = customerService.getAllCustomerDetails();

        assertFalse(customers.isEmpty());
    }

    @Test
    void shouldGetCustomerDetailsById() {
        CustomerDetails customer = customerService.getCustomerDetailsById(1L);

        assertNotNull(customer);
        assertEquals(1L, customer.getId());
    }

    @Test
    void shouldThrowExceptionIfCustomerNotFound() {
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerDetailsById(99L));
    }
}
