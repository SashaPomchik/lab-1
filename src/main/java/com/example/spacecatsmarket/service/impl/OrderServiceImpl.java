package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.order.Order;
import com.example.spacecatsmarket.domain.order.OrderContext;
import com.example.spacecatsmarket.domain.order.OrderEntry;
import com.example.spacecatsmarket.domain.payment.PaymentTransaction;
import com.example.spacecatsmarket.service.interfaces.OrderService;
import com.example.spacecatsmarket.service.interfaces.PaymentService;
import com.example.spacecatsmarket.service.exception.PaymentTransactionFailed;
import com.example.spacecatsmarket.web.mapper.PaymentServiceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.example.spacecatsmarket.common.PaymentStatus.FAILURE;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final PaymentService paymentService;
    private final PaymentServiceMapper paymentMapper;

    public OrderServiceImpl(PaymentService paymentService, PaymentServiceMapper paymentMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }


    @Override
    public Order placeOrder(OrderContext orderContext) {
        log.info("Placing order for cart with id: {}", orderContext.getCartId());
        PaymentTransaction paymentTransaction = paymentService.processPayment(paymentMapper.toPayment(orderContext));
        if (FAILURE.equals(paymentTransaction.getStatus())) {
            throw new PaymentTransactionFailed(paymentTransaction.getId(), orderContext.getCartId());
        }
        // TODO add mock for order service
        return createOrderMock(orderContext.getCartId(),
            orderContext.getEntries(),
            orderContext.getTotalPrice(),
            orderContext.getCustomerReference(),
            paymentTransaction.getId());
    }

    private Order createOrderMock(String cartId, List<OrderEntry> entries, Double totalPrice, String consumerReference, UUID transactionId) {
        return Order.builder()
            .id(UUID.randomUUID().toString())
            .transactionId(transactionId)
            .cartId(cartId)
            .entries(entries)
            .totalPrice(totalPrice)
            .consumerReference(consumerReference)
            .build();
    }
}
