package com.acme.fromzeroapi.projects.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record CreateDeliverableCommand(
        String name,
        String description,
        LocalDate date,
        Long projectId
        /*List<MultipartFile> files*/
) {
    /*public CreateDeliverableCommand(
            String name,
            String description,
            LocalDate date,
            Long projectId
    ) {
        this(name, description, date, projectId, new ArrayList<>());
    }*/
}
