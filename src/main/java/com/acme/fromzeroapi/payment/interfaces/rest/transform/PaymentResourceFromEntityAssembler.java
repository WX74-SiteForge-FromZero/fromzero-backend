package com.acme.fromzeroapi.payment.interfaces.rest.transform;

import com.acme.fromzeroapi.payment.domain.model.aggregates.Payment;
import com.acme.fromzeroapi.payment.interfaces.rest.resources.PaymentResource;

import java.text.SimpleDateFormat;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment entity){
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String createdAtFormatted = dateFormat.format(entity.getCreatedAt());
        String completedAtFormatted = dateFormat.format(entity.getUpdatedAt());
        return new PaymentResource(
          entity.getId(),
          entity.getDeveloper().getId(),
          //entity.getProject().getId(),
          entity.getProject(),
          entity.getAmount().paymentAmountToString(),
          entity.getStatus(),
          createdAtFormatted,
          completedAtFormatted
        );
    }
}
