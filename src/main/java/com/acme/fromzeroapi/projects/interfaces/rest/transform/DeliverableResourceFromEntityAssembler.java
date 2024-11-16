package com.acme.fromzeroapi.projects.interfaces.rest.transform;

import com.acme.fromzeroapi.projects.domain.model.entities.Deliverable;
import com.acme.fromzeroapi.projects.interfaces.rest.resources.DeliverableResource;
import com.acme.fromzeroapi.projects.interfaces.rest.resources.FileResource;

import java.util.stream.Collectors;

public class DeliverableResourceFromEntityAssembler {
    public static DeliverableResource toResourceFromEntity(Deliverable entity){
        return new DeliverableResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getDate(),
                entity.getState(),
                entity.getDeveloperMessage(),
                entity.getProject().getId(),
                entity.getFiles().stream()
                        .map(item->
                                new FileResource(
                                        item.getId(),
                                        item.getName(),
                                        item.getUrl()
                                )).collect(Collectors.toList())
        );
    }
}
