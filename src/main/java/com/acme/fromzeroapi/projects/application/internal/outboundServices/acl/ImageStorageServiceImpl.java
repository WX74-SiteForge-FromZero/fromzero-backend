package com.acme.fromzeroapi.projects.application.internal.outboundServices.acl;

import com.acme.fromzeroapi.projects.application.internal.outboundServices.acl.services.ImageStorageService;
import com.azure.storage.blob.BlobServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;
    private final BlobServiceClient blobServiceClient;

    public ImageStorageServiceImpl(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    @Override
    public String uploadImage(String originalName, InputStream data, long length) throws IOException {
        var blobContainer = blobServiceClient.getBlobContainerClient(this.containerName);
        var newFileName = UUID.randomUUID().toString() + originalName.substring(originalName.lastIndexOf("."));
        var blobClient = blobContainer.getBlobClient(newFileName);
        blobClient.upload(data, length,true);
        return blobClient.getBlobUrl();
    }
}
