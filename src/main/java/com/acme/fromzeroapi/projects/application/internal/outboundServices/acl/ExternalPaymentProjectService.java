package com.acme.fromzeroapi.projects.application.internal.outboundServices.acl;

import com.acme.fromzeroapi.payment.domain.model.aggregates.Payment;
import com.acme.fromzeroapi.payment.interfaces.acl.PaymentContextFacade;
import com.acme.fromzeroapi.profiles.domain.model.aggregates.Developer;
import com.acme.fromzeroapi.projects.domain.model.aggregates.Project;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalPaymentProjectService {
    private final PaymentContextFacade paymentContextFacade;

    public ExternalPaymentProjectService(PaymentContextFacade paymentContextFacade) {
        this.paymentContextFacade = paymentContextFacade;
    }

    public Optional<Payment> createProjectPayment(Long projectId) {
        return paymentContextFacade.createPayment(projectId);
    }
}
