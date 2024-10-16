package com.acme.fromzeroapi.payment.domain.services;

import com.acme.fromzeroapi.payment.domain.model.aggregates.Payment;

import java.util.Optional;

public interface PaymentQueryService {
    Optional<Payment> handle(Long projectId);
}
