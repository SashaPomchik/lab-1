package com.example.spacecatsmarket.service.interfaces;

import com.example.spacecatsmarket.domain.payment.Payment;
import com.example.spacecatsmarket.domain.payment.PaymentTransaction;

public interface PaymentService {

    PaymentTransaction processPayment(Payment payment);

}
