package com.acme.fromzeroapi.projects.domain.model.commands;

public record CreateDeliverableFileCommand(
        String name,
        String url
) {
}
