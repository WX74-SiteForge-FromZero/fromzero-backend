package com.acme.fromzeroapi.payment.domain.model.aggregates;

import com.acme.fromzeroapi.payment.domain.model.commands.CompletePaymentCommand;
import com.acme.fromzeroapi.payment.domain.model.commands.CreatePaymentCommand;
import com.acme.fromzeroapi.payment.domain.model.events.PaymentCompletedEvent;
import com.acme.fromzeroapi.payment.domain.model.valueObjects.*;
import com.acme.fromzeroapi.profiles.domain.model.aggregates.Developer;
import com.acme.fromzeroapi.projects.domain.model.aggregates.Project;
import com.acme.fromzeroapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
public class Payment extends AuditableAbstractAggregateRoot<Payment> {
    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "project_id", unique = true, nullable = false
    )
    private Project project;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Setter
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Embedded
    private Card card;

    // created with acl
    /*public Payment(
            Developer developer,
            Project project,
            PaymentMethod paymentMethod
    ){
        this.developer = developer;
        this.project = project;
        this.amount=new Amount();
        this.status=PaymentStatus.PENDIENTE;
        this.paymentMethod=paymentMethod;
        this.card=new Card();
    }*/
    public Payment(Project project) {
        /*this.developer = command.developer();
        this.project = command.project();
        this.amount = command.project().getBudget().budget();
        this.status = PaymentStatus.PENDIENTE;
        this.currency = Currency.valueOf(command.project().getBudget().currency().toString());
        this.card = new Card();*/
        this.developer = project.getDeveloper();
        this.project = project;
        this.amount = project.getBudget().budget();
        this.status = PaymentStatus.PENDIENTE;
        this.currency = Currency.valueOf(project.getBudget().currency().toString());
        this.card = new Card();
    }
    public void updateCard(CompletePaymentCommand command){
        this.card=new Card(command.cardNumber(),command.expirationDate(),command.cvv());
    }
    public void finishProject(Long projectId){
        this.registerEvent(new PaymentCompletedEvent(this,projectId));
    }
}
