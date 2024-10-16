package com.acme.fromzeroapi.projects.domain.services;

import com.acme.fromzeroapi.projects.domain.model.aggregates.Project;
import com.acme.fromzeroapi.projects.domain.model.commands.*;

import java.util.Optional;

public interface ProjectCommandService {
    Optional<Project> handle(CreateProjectCommand command);
    Optional<Project> handle(UpdateProjectCandidatesListCommand command);
    Optional<Project> handle(AssignProjectDeveloperCommand command);
    void handle(UpdateProjectProgressCommand command);
    void handle(FinishProjectCommand command);
}
