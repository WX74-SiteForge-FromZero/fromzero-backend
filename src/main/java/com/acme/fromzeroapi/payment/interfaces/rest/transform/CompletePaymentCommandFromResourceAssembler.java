package com.acme.fromzeroapi.payment.interfaces.rest.transform;

import com.acme.fromzeroapi.payment.domain.model.commands.CompletePaymentCommand;
import com.acme.fromzeroapi.payment.interfaces.rest.resources.CompletePaymentResource;

public class CompletePaymentCommandFromResourceAssembler {
    public static CompletePaymentCommand toCommandFromResource(CompletePaymentResource resource, Long projectId){
        return new CompletePaymentCommand(
                projectId,
                resource.cardNumber(),
                resource.expirationDate(),
                resource.cvv()
        );
    }
}
