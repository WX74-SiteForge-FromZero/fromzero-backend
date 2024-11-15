package com.acme.fromzeroapi.projects.interfaces.rest.transform;

import com.acme.fromzeroapi.projects.domain.model.commands.CreateDeliverableCommand;
import com.acme.fromzeroapi.projects.interfaces.rest.resources.CreateDeliverableResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class CreateDeliverableCommandFromResourceAssembler {
    public static CreateDeliverableCommand toCommandFromResource(CreateDeliverableResource resource/*, List<MultipartFile> files*/){
        //var safeFiles = files!=null?files:new ArrayList<MultipartFile>();
        return new CreateDeliverableCommand(
                resource.name(),
                resource.description(),
                resource.date(),
                resource.projectId()
                //safeFiles
        );
    }
}
