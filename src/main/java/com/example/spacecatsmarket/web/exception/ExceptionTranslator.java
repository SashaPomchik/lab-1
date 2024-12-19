package com.example.spacecatsmarket.web.exception;

import com.example.spacecatsmarket.featuretoggle.exception.FeatureToggleNotEnabledException;
import com.example.spacecatsmarket.service.exception.*;
import com.example.spacecatsmarket.web.exception.CatNotFoundException;
import com.example.spacecatsmarket.web.exception.ParamsViolationDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static com.example.spacecatsmarket.util.PaymentDetailsUtils.getValidationErrorsProblemDetail;
import static java.net.URI.create;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    ProblemDetail handleCustomerNotFoundException(CustomerNotFoundException ex) {
        log.info("Customer Not Found exception raised");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(create("customer-not-found"));
        problemDetail.setTitle("Customer Not Found");
        return problemDetail;
    }

    @ExceptionHandler(CatNotFoundException.class)
    ProblemDetail handleCatNotFoundException(CatNotFoundException ex) {
        log.info("Cat Not Found exception raised");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(create("cat-not-found"));
        problemDetail.setTitle("Cat Not Found");
        return problemDetail;
    }

    @ExceptionHandler(PaymentTransactionFailed.class)
    ProblemDetail handlePaymentTransactionFailedException(PaymentTransactionFailed ex) {
        log.info("Payment Transaction Failed");
        ProblemDetail problemDetail = forStatusAndDetail(BAD_REQUEST, ex.getMessage());
        problemDetail.setType(create("payment-refused"));
        problemDetail.setTitle("Payment Transaction Failed to process");
        return problemDetail;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail handleProductNotFoundException(ProductNotFoundException ex) {
        log.info("Product Not Found exception raised");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(create("product-not-found"));
        problemDetail.setTitle("Product Not Found");
        return problemDetail;
    }

    @ExceptionHandler(WishlistEntryNotFoundException.class)
    ProblemDetail handleWishlistEntryNotFoundException(WishlistEntryNotFoundException ex) {
        log.info("Wishlist Entry Not Found exception raised");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(create("wishlist-entry-not-found"));
        problemDetail.setTitle("Wishlist Entry Not Found");
        return problemDetail;
    }

    @ExceptionHandler(PaymentClientFailedProcessPayment.class)
    ProblemDetail handlePaymentClientFailedProcessPayment(PaymentClientFailedProcessPayment ex) {
        log.info("Payment Client Failed to Process Payment");
        ProblemDetail problemDetail = forStatusAndDetail(BAD_REQUEST, ex.getMessage());
        problemDetail.setType(create("payment-client-failure"));
        problemDetail.setTitle("Payment Client Failed to Process Payment");
        return problemDetail;
    }

    @ExceptionHandler(FeatureToggleNotEnabledException.class)
    ProblemDetail handleFeatureToggleNotEnabledException(FeatureToggleNotEnabledException ex) {
        log.info("Feature is not enabled");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(create("feature-disabled"));
        problemDetail.setTitle("Feature is disabled");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<ParamsViolationDetails> validationResponse =
                errors.stream()
                        .map(err -> ParamsViolationDetails.builder()
                                .reason(err.getDefaultMessage())
                                .fieldName(err.getField())
                                .build())
                        .toList();
        log.info("Input params validation failed");
        return ResponseEntity.status(BAD_REQUEST).body(getValidationErrorsProblemDetail(validationResponse));
    }
}
