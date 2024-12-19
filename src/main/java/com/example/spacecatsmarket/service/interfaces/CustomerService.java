package com.example.spacecatsmarket.service.interfaces;

import com.example.spacecatsmarket.domain.customer.CustomerDetails;

import java.util.List;

public interface CustomerService {

    List<CustomerDetails> getAllCustomerDetails();

    CustomerDetails getCustomerDetailsById(Long customerId);
}
