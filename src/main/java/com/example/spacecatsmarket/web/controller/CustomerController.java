package com.example.spacecatsmarket.web.controller;

import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsListDto;
import com.example.spacecatsmarket.service.interfaces.CustomerService;
import com.example.spacecatsmarket.web.mapper.CustomDetailsMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomDetailsMapper customDetailsMapper;

    public CustomerController(CustomerService customerService, CustomDetailsMapper customDetailsMapper) {
        this.customerService = customerService;
        this.customDetailsMapper = customDetailsMapper;
    }

    @GetMapping
    public ResponseEntity<CustomerDetailsListDto> getAllCustomers() {
        return ResponseEntity.ok(customDetailsMapper.toCustomerDetailsListDto(customerService.getAllCustomerDetails()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDetailsDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customDetailsMapper.toCustomerDetailsDto(customerService.getCustomerDetailsById(id)));
    }

    @PostMapping
    public ResponseEntity<CustomerDetailsDto> createCustomer(@RequestBody @Valid CustomerDetailsDto customerDetailsDto){
        return ResponseEntity.ok(customerDetailsDto);
    }
}
