package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.common.PaymentStatus;
import com.example.spacecatsmarket.common.ProductType;
import com.example.spacecatsmarket.config.MappersTestConfiguration;
import com.example.spacecatsmarket.domain.order.Order;
import com.example.spacecatsmarket.domain.order.OrderContext;
import com.example.spacecatsmarket.domain.order.OrderEntry;
import com.example.spacecatsmarket.domain.payment.Payment;
import com.example.spacecatsmarket.domain.payment.PaymentTransaction;
import com.example.spacecatsmarket.service.exception.PaymentTransactionFailed;
import com.example.spacecatsmarket.service.impl.OrderServiceImpl;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import com.example.spacecatsmarket.service.interfaces.OrderService;
import com.example.spacecatsmarket.service.interfaces.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {OrderServiceImpl.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Order Service Tests")
@TestMethodOrder(OrderAnnotation.class)
class OrderServiceTest {

    private static final OrderEntry DEFAULT_ORDER_ENTRY = buildOrderEntry();
    private static final OrderContext ORDER_CONTEXT = buildOrderContext("cartId");
    private static final String CART_ID = "cartId";
    private static final String CUSTOMER_REFERENCE = "customerRef";
    private static final double TOTAL_PRICE = 105.0;

    @MockBean
    private PaymentService paymentService;

    @Captor
    private ArgumentCaptor<Payment> paymentArgumentCaptor;

    @Autowired
    private OrderService orderService;

    private static Stream<OrderContext> provideOrderContexts() {
        return Stream.of(
            buildOrderContext("cartTest"),
            buildOrderContext("cartSuperTest")
            // Add more variations if needed
        );
    }

    @ParameterizedTest
    @MethodSource("provideOrderContexts")
    @org.junit.jupiter.api.Order(1)
//    @EnabledIf(value = "#{environment['tests.unit-tests.enabled'] == 'true'}", loadContext = true)
    void shouldPlaceOrder(OrderContext orderContext) {

        when(paymentService.processPayment(paymentArgumentCaptor.capture())).thenAnswer(inv -> buildPaymentTransaction(((Payment) inv.getArgument(0)).getCartId(),
            PaymentStatus.SUCCESS));


        Order result = orderService.placeOrder(orderContext);

        verify(paymentService, times(1)).processPayment(any());
        assertThatNoException().isThrownBy(() -> orderService.placeOrder(orderContext));

        assertEquals(orderContext.getCartId(), result.getCartId());
        assertEquals(CUSTOMER_REFERENCE, result.getConsumerReference());
        assertEquals(TOTAL_PRICE, result.getTotalPrice());
        assertEquals(TOTAL_PRICE, result.getTotalPrice());
        assertEquals(DEFAULT_ORDER_ENTRY, result.getEntries().get(0));

    }

    @Test
    void shouldThrowExceptionPaymentTransactionFailed(){

        when(paymentService.processPayment(paymentArgumentCaptor.capture())).thenAnswer(inv -> buildPaymentTransaction(((Payment) inv.getArgument(0)).getCartId(),
            PaymentStatus.FAILURE));

        assertThrows(PaymentTransactionFailed.class, () -> orderService.placeOrder(ORDER_CONTEXT));
    }

    private static OrderContext buildOrderContext(String cartId) {
        return OrderContext.builder()
            .cartId(cartId)
            .customerReference(CUSTOMER_REFERENCE)
            .totalPrice(TOTAL_PRICE)
            .entries(List.of(DEFAULT_ORDER_ENTRY))
            .build();
    }

    private static OrderEntry buildOrderEntry() {
        return OrderEntry.builder().amount(1).productType(ProductType.COSMIC_CATNIP).build();
    }

    private PaymentTransaction buildPaymentTransaction(String cartId, PaymentStatus paymentStatus) {
        return PaymentTransaction.builder().id(UUID.randomUUID()).cartId(cartId).status(paymentStatus).build();
    }


}
