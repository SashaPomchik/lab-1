package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.payment.Payment;
import com.example.spacecatsmarket.domain.payment.PaymentTransaction;
import com.example.spacecatsmarket.dto.payment.PaymentClientRequestDto;
import com.example.spacecatsmarket.dto.payment.PaymentClientResponseDto;
import com.example.spacecatsmarket.service.interfaces.PaymentService;
import com.example.spacecatsmarket.service.exception.PaymentClientFailedProcessPayment;
import com.example.spacecatsmarket.web.mapper.PaymentServiceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${application.payment-service.timeout:5000}")
    private int paymentServiceTimeout;

    private final RestClient paymentClient;
    private final PaymentServiceMapper paymentServiceMapper;
    private final String paymentServiceEndpoint;

    public PaymentServiceImpl(@Qualifier("paymentRestClient") RestClient paymentClient,
                              PaymentServiceMapper paymentServiceMapper,
                              @Value("${application.payment-service.payments}") String paymentServiceEndpoint)
    {
        this.paymentClient = paymentClient;
        this.paymentServiceMapper = paymentServiceMapper;
        this.paymentServiceEndpoint = paymentServiceEndpoint;
    }

    public PaymentTransaction processPayment(Payment payment) {
        log.info("Processing payment for cart with id {} and consumer reference: {}", payment.getCartId(), payment.getConsumerReference());
        PaymentClientRequestDto paymentClientRequestDto = paymentServiceMapper.toPaymentClientRequestDto(payment);

        PaymentClientResponseDto paymentClientResponseDto = paymentClient.post()
            .uri(paymentServiceEndpoint)
            .body(paymentClientRequestDto)
            .contentType(APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, (request, response) -> {
                log.error("Server response failed to fetch payment link. Response Code {}", response.getStatusCode());
                throw new PaymentClientFailedProcessPayment(payment.getCartId(), payment.getConsumerReference());
            })
            .body(PaymentClientResponseDto.class);


        return paymentServiceMapper.toPaymentTransaction(payment.getCartId(), paymentClientResponseDto);
    }

}
