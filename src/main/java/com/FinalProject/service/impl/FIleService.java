package com.FinalProject.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FIleService {
    public Resource load(String filename);
    public void save(MultipartFile multipartFile);
    public void deleteFile(String fileName);
    public boolean isPng(MultipartFile file);
}
