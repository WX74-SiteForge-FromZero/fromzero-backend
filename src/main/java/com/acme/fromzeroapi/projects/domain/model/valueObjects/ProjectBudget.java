package com.acme.fromzeroapi.projects.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProjectBudget(Double budget, ProjectCurrency currency) {
    public String projectBudgetToString(){
        return budget+" "+currency;
    }
}
