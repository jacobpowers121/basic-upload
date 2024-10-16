package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResourceService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save file path to the database
    public void saveFilePath(String filePath) {
        String sql = "INSERT INTO file_uploads (file_path) VALUES (?)";
        jdbcTemplate.update(sql, filePath);
    }

    // Get all file paths from the database
    public List<String> getAllFiles() {
        String sql = "SELECT file_path FROM file_uploads";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}
