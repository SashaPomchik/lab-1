package com.example.spacecatsmarket.domain.customer;

import com.example.spacecatsmarket.common.CommunicationChannel;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CustomerDetails {

    Long id;
    String name;
    String address;
    String phoneNumber;
    String email;
    List<CommunicationChannel> preferredChannel;
}
