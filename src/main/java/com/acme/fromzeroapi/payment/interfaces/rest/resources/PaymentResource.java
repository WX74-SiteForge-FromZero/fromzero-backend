package com.acme.fromzeroapi.payment.interfaces.rest.resources;

import com.acme.fromzeroapi.payment.domain.model.valueObjects.Card;
import com.acme.fromzeroapi.payment.domain.model.valueObjects.Currency;
import com.acme.fromzeroapi.payment.domain.model.valueObjects.PaymentStatus;
import com.acme.fromzeroapi.profiles.domain.model.aggregates.Developer;
import com.acme.fromzeroapi.projects.domain.model.aggregates.Project;

import java.time.LocalDate;

public record PaymentResource(
        Long id,
        Long developerId,
        Long projectId,
        Double amount,
        Currency currency,
        PaymentStatus status,
        /*String cardNumber,
        LocalDate expirationDate,
        String cvv*/
        Card card
){

}
