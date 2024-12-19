package com.example.spacecatsmarket.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@TestConfiguration
public class TestRestClientConfiguration {

    @Bean("paymentRestClient")
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS))
                .build();
    }
}
