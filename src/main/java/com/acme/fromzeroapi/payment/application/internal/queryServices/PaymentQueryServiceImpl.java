package com.acme.fromzeroapi.payment.application.internal.queryServices;

import com.acme.fromzeroapi.payment.application.internal.outboundservices.acl.ExternalProjectPaymentService;
import com.acme.fromzeroapi.payment.domain.model.aggregates.Payment;
import com.acme.fromzeroapi.payment.domain.services.PaymentQueryService;
import com.acme.fromzeroapi.payment.infrastructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {
    private final PaymentRepository paymentRepository;
    private final ExternalProjectPaymentService externalProjectPaymentService;

    public PaymentQueryServiceImpl(
            PaymentRepository paymentRepository,
            ExternalProjectPaymentService externalProjectPaymentService
    ) {
        this.paymentRepository = paymentRepository;
        this.externalProjectPaymentService = externalProjectPaymentService;
    }

    @Override
    public Optional<Payment> handle(Long projectId) {
        var project = externalProjectPaymentService.fetchProject(projectId);
        if (project.isEmpty()){
            return Optional.empty();
        }
        return paymentRepository.findByProject(project.get());
    }
}
