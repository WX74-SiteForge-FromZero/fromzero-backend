package com.acme.fromzeroapi.payment.interfaces.acl;

import com.acme.fromzeroapi.payment.domain.model.aggregates.Payment;
import com.acme.fromzeroapi.payment.domain.model.commands.CreatePaymentCommand;
import com.acme.fromzeroapi.payment.domain.services.PaymentCommandService;
import com.acme.fromzeroapi.profiles.domain.model.aggregates.Developer;
import com.acme.fromzeroapi.projects.domain.model.aggregates.Project;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentContextFacade {
    private final PaymentCommandService paymentCommandService;

    public PaymentContextFacade(PaymentCommandService paymentCommandService) {
        this.paymentCommandService = paymentCommandService;
    }

    public Optional<Payment> createPayment(Long projectId){
        /*return paymentCommandService.handle(project, developer);*/
        return paymentCommandService.handle(new CreatePaymentCommand(projectId));
    }
}
