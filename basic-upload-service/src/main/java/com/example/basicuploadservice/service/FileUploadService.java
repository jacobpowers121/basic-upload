package com.example.basicuploadservice.service;

import com.example.basicuploadservice.client.FileSaveClient;
import com.example.basicuploadservice.client.ResourceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Service
public class FileUploadService {


    private final ResourceClient resourceClient;
    private final FileSaveClient fileSaveClient;

    @Autowired
    public FileUploadService(ResourceClient resourceClient, FileSaveClient fileSaveClient) {
        this.resourceClient = resourceClient;
        this.fileSaveClient = fileSaveClient;
    }

    // Store the uploaded file on disk and save the path to the database
    public String storeFile(MultipartFile file) throws IOException {

        String filePath = fileSaveClient.saveFilePath(file);
        resourceClient.saveFilePath(filePath);

        return filePath;
    }

    public ResponseEntity<Resource> getFileByFileName(String fileName) throws MalformedURLException {
        return fileSaveClient.getFileByFileName(fileName);
    }

    // Retrieve all file paths from the database
    public List<String> getAllFiles() {
        return resourceClient.getAllFiles();
    }
}
