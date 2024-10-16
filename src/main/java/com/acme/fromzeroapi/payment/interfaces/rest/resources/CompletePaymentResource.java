package com.acme.fromzeroapi.payment.interfaces.rest.resources;

import java.time.LocalDate;

public record CompletePaymentResource(
        String cardNumber,
        LocalDate expirationDate,
        String cvv
) {
}
