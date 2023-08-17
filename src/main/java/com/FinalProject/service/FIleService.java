package com.FinalProject.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FIleService {
    Resource load(String filename);

    void save(MultipartFile multipartFile);

    void deleteFile(String fileName);

    boolean isPng(MultipartFile file);
}
