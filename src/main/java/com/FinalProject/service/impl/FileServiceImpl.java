package com.FinalProject.service.impl;

import com.FinalProject.service.FIleService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileServiceImpl implements FIleService {

    private final static Path root = Path.of("src/main/resources/static/images");

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void save(MultipartFile multipartFile) {
        if (isPng(multipartFile)) {
            try {
                Files.copy(
                        multipartFile.getInputStream(),
                        root.resolve((Objects.requireNonNull(multipartFile.getOriginalFilename()))),
                        StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Only PNG files are allowed");
        }

    }

    @Override
    public void deleteFile(String fileName) {
        Path path = Paths.get(root.toString(), fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPng(MultipartFile file) {
        return file.getContentType().equals("image/png");
    }

}
