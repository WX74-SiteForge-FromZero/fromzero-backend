package com.acme.fromzeroapi.projects.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record UpdateDeveloperMessageCommand(
        Long deliverableId,
        String message,
        List<MultipartFile> files
) {
    public UpdateDeveloperMessageCommand(
            Long deliverableId,
            String message,
            List<MultipartFile> files
    ){
        this.deliverableId = deliverableId;
        this.message = message;
        this.files = (files!=null)?files:new ArrayList<>();
    }
}
