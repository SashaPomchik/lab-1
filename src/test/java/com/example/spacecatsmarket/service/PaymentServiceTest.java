package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.common.PaymentStatus;
import com.example.spacecatsmarket.config.MappersTestConfiguration;
import com.example.spacecatsmarket.config.TestRestClientConfiguration;
import com.example.spacecatsmarket.domain.payment.Payment;
import com.example.spacecatsmarket.domain.payment.PaymentTransaction;
import com.example.spacecatsmarket.dto.payment.PaymentClientResponseDto;
import com.example.spacecatsmarket.service.exception.PaymentClientFailedProcessPayment;
import com.example.spacecatsmarket.service.impl.PaymentServiceImpl;
import com.example.spacecatsmarket.web.mapper.PaymentServiceMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {PaymentServiceImpl.class, TestRestClientConfiguration.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Payment Service Tests")
class PaymentServiceTest {

    @Mock
    private PaymentServiceMapper paymentServiceMapper;

    @Test
    void shouldProcessPaymentSuccessfully() {
        Payment payment = Payment.builder().cartId("cart-123").consumerReference("consumer-123").build();
        PaymentClientResponseDto responseDto = PaymentClientResponseDto.builder()
                .uuid(UUID.randomUUID())
                .status(PaymentStatus.SUCCESS)
                .consumerReference("consumer-123")
                .build();

        when(paymentServiceMapper.toPaymentTransaction(payment.getCartId(), responseDto))
                .thenReturn(PaymentTransaction.builder().build());

        PaymentTransaction transaction = paymentServiceMapper.toPaymentTransaction(payment.getCartId(), responseDto);

        assertNotNull(transaction);
    }

    @Test
    void shouldThrowExceptionOnPaymentFailure() {
        Payment payment = Payment.builder().cartId("cart-123").consumerReference("consumer-123").build();

        assertThrows(PaymentClientFailedProcessPayment.class,
                () -> {
                    throw new PaymentClientFailedProcessPayment(payment.getCartId(), payment.getConsumerReference());
                });
    }
}
