package com.acme.fromzeroapi.payment.interfaces.rest.resources;

import com.acme.fromzeroapi.payment.domain.model.valueObjects.PaymentStatus;
import com.acme.fromzeroapi.projects.domain.model.aggregates.Project;

public record PaymentResource(
        Long id,
        Long developerId,
        Project project,
        String amount,
        PaymentStatus status,
        String createdAt,
        String completedAt
){

}
