package com.example.spacecatsmarket.dto.customer;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CustomerDetailsEntry {
    Long id;
    String name;
    String address;
    String phoneNumber;
    String email;
    List<String> preferredChannel;
}