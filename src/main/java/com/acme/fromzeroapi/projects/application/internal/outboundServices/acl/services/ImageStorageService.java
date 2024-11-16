package com.acme.fromzeroapi.projects.application.internal.outboundServices.acl.services;

import java.io.IOException;
import java.io.InputStream;

public interface ImageStorageService {
    String uploadImage(String originalName, InputStream data, long length) throws IOException;
}
