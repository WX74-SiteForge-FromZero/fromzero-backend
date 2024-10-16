package com.acme.fromzeroapi.payment.interfaces.rest.resources;

import com.acme.fromzeroapi.payment.domain.model.valueObjects.Card;
import com.acme.fromzeroapi.payment.domain.model.valueObjects.Currency;
import com.acme.fromzeroapi.payment.domain.model.valueObjects.PaymentStatus;

public record PaymentResource(
        Long id,
        Long developerId,
        Long projectId,
        Double amount,
        Currency currency,
        PaymentStatus status,
        Card card
){

}
