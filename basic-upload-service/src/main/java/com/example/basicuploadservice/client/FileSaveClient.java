package com.example.basicuploadservice.client;

import jakarta.servlet.ServletContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

@Component
public class FileSaveClient {

    private static final String UPLOAD_DIRECTORY = "/home/jacob/dev/basic-upload/shared-volumes/uploaded_files"; // Make sure this matches your Docker volume
    private static final Logger logger = Logger.getLogger(FileSaveClient.class.getName());
    private final ServletContext servletContext;

    public FileSaveClient(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String saveFilePath(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file");
        }
        Path filePath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    public ResponseEntity<Resource> getFileByFileName(String fileName) throws MalformedURLException {
        Path filePath = Paths.get(UPLOAD_DIRECTORY).resolve(fileName).normalize();
        logger.info("Trying to access file at path: " + filePath);

        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            // Determine the file's content type
            String mimeType = servletContext.getMimeType(filePath.toString());
            if (mimeType == null) {
                // Fallback to binary type if MIME type is not determined
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            logger.warning("File not found or not readable: " + filePath);
            throw new RuntimeException("File not found: " + fileName);
        }
    }

}
