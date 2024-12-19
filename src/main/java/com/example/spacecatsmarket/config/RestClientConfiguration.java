package com.example.spacecatsmarket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Slf4j
@Configuration
public class RestClientConfiguration {

    @Value("${application.payment-service.timeout:5000}")
    private final int responseTimeout;

    public RestClientConfiguration(@Value("${application.restclient.response-timeout:1000}") int responseTimeout) {
        this.responseTimeout = responseTimeout;
        log.info("RestClientConfiguration initialized with response timeout: {} ms", responseTimeout);
    }

    @Bean("paymentRestClient")
    public RestClient paymentRestClient() {
        log.info("Creating RestClient bean with name 'paymentRestClient'");
        return RestClient.builder()
                .requestFactory(getClientHttpRequestFactory(responseTimeout))
                .build();
    }

    private static ClientHttpRequestFactory getClientHttpRequestFactory(int responseTimeout) {
        log.info("Configuring ClientHttpRequestFactory with response timeout: {} ms", responseTimeout);
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withReadTimeout(Duration.ofMillis(responseTimeout));
        return ClientHttpRequestFactories.get(settings);
    }
}
