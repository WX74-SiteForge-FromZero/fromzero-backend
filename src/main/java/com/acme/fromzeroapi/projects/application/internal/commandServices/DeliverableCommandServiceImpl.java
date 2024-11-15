package com.acme.fromzeroapi.projects.application.internal.commandServices;

import com.acme.fromzeroapi.projects.application.internal.outboundServices.acl.services.ImageStorageService;
import com.acme.fromzeroapi.projects.domain.exceptions.DeliverableFilesProcessingException;
import com.acme.fromzeroapi.projects.domain.model.aggregates.Project;
import com.acme.fromzeroapi.projects.domain.model.commands.CreateDeliverableCommand;
import com.acme.fromzeroapi.projects.domain.model.commands.CreateDeliverableFileCommand;
import com.acme.fromzeroapi.projects.domain.model.entities.Deliverable;
import com.acme.fromzeroapi.projects.domain.model.commands.UpdateDeliverableStatusCommand;
import com.acme.fromzeroapi.projects.domain.model.commands.UpdateDeveloperMessageCommand;
import com.acme.fromzeroapi.projects.domain.model.entities.File;
import com.acme.fromzeroapi.projects.domain.model.valueObjects.DeliverableState;
import com.acme.fromzeroapi.projects.domain.services.DeliverableCommandService;
import com.acme.fromzeroapi.projects.infrastructure.persistence.jpa.repositories.DeliverableRepository;
import com.acme.fromzeroapi.projects.infrastructure.persistence.jpa.repositories.ProjectRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeliverableCommandServiceImpl implements DeliverableCommandService {

    private final DeliverableRepository deliverableRepository;
    private final ProjectRepository projectRepository;

    private final ImageStorageService imageStorageService;

    private final ApplicationEventPublisher eventPublisher;

    public DeliverableCommandServiceImpl(
            DeliverableRepository deliverableRepository,
            ProjectRepository projectRepository,
            ImageStorageService imageStorageService,
            ApplicationEventPublisher eventPublisher) {

        this.deliverableRepository = deliverableRepository;
        this.projectRepository = projectRepository;
        this.imageStorageService = imageStorageService;
        this.eventPublisher = eventPublisher;
    }

    public List<Deliverable> getDeliverables(Project project) {
        return deliverableRepository.findAllByProject(project);
    }

    private List<File> createFiles(List<MultipartFile> files) {
        List<File> fileList = new ArrayList<>();
        for(var file:files){
            try (InputStream inputStream=file.getInputStream()){
                var name = file.getOriginalFilename();
                var fileUrl = imageStorageService.uploadImage(
                        file.getOriginalFilename(),
                        inputStream,
                        file.getSize()
                );
                var createdFile = new File(new CreateDeliverableFileCommand(name,fileUrl));
                fileList.add(createdFile);
            }catch (IOException ioException){
                throw new DeliverableFilesProcessingException("Error processing the file. "+ioException.getMessage());
            }catch (Exception e){
                throw new DeliverableFilesProcessingException("Error processing files. "+e.getMessage());
            }
        }
        return fileList;
    }

    @Override
    public Optional<Deliverable> handle(CreateDeliverableCommand command) {

        var project = projectRepository.findById(command.projectId());
        if (project.isEmpty()) {
            return Optional.empty();
        }
        var deliverable = new Deliverable(command, project.get());

        this.deliverableRepository.save(deliverable);

        var deliverables = getDeliverables(project.get());
        Long completedDeliverables = deliverables.stream()
                .filter(item -> DeliverableState.COMPLETADO.equals(item.getState()))
                .count();
        Integer totalDeliverables = deliverables.size();

        deliverable.updateProjectProgress(project.get().getId(), completedDeliverables, totalDeliverables);

        deliverable.getDomainEvents().forEach(eventPublisher::publishEvent);

        return Optional.of(deliverable);
    }

    @Override
    public void handle(List<CreateDeliverableCommand> commands) {
        commands.forEach(this::handle);
    }

    @Override
    public Optional<Deliverable> handle(UpdateDeveloperMessageCommand command) {

        var deliverable = deliverableRepository.findById(command.deliverableId());
        if (deliverable.isEmpty()) {
            return Optional.empty();
        }
        if (deliverable.get().getProject().getDeveloper()==null){
            return Optional.empty();
        }

        var files = this.createFiles(command.files());
        deliverable.get().setFiles(files);
        deliverable.get().setDeveloperMessage(command.message());
        deliverable.get().setState(DeliverableState.ESPERANDO_REVISION);
        this.deliverableRepository.save(deliverable.get());
        return deliverable;

    }

    @Override
    public Optional<Deliverable> handle(UpdateDeliverableStatusCommand command) {

        var deliverable = this.deliverableRepository.findById(command.deliverableId());
        if (deliverable.isEmpty()) {
            return Optional.empty();
        }

        if (deliverable.get().getProject().getDeveloper()==null){
            return Optional.empty();
        }

        if (deliverable.get().getDeveloperMessage()==null){
            return Optional.empty();
        }

        if (command.accepted()) {
            deliverable.get().setState(DeliverableState.COMPLETADO);
        } else {
            deliverable.get().setState(DeliverableState.RECHAZADO);
        }
        this.deliverableRepository.save(deliverable.get());

        var deliverables = getDeliverables(deliverable.get().getProject());
        Long completedDeliverables = deliverables.stream()
                .filter(item -> DeliverableState.COMPLETADO.equals(item.getState()))
                .count();
        Integer totalDeliverables = deliverables.size();

        deliverable.get().updateProjectProgress(deliverable.get().getProject().getId(), completedDeliverables, totalDeliverables);

        deliverable.get().getDomainEvents().forEach(eventPublisher::publishEvent);

        return deliverable;
    }

}
