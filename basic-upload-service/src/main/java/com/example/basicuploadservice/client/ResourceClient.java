package com.example.basicuploadservice.client;

import org.example.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResourceClient {

    private final ResourceService resourceService;

    @Autowired
    public ResourceClient(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public void saveFilePath(String filePath) {
        resourceService.saveFilePath(filePath);
    }

    public List<String> getAllFiles() {
        return resourceService.getAllFiles();
    }
}
