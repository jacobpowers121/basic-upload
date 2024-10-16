package com.example.basicuploadservice.controller;

import com.example.basicuploadservice.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String filePath = fileUploadService.storeFile(file);
        return new ResponseEntity<>("File uploaded successfully: " + filePath, HttpStatus.OK);
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName) throws IOException {
        return fileUploadService.getFileByFileName(fileName);
    }

    // Endpoint to retrieve all files
    @GetMapping("/files")
    public ResponseEntity<List<String>> getFiles() {
        return new ResponseEntity<>(fileUploadService.getAllFiles(), HttpStatus.OK);
    }
}
