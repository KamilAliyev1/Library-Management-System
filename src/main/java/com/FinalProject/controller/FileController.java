package com.FinalProject.controller;

import com.FinalProject.service.impl.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileServiceImpl fileService;


    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getBookImage(@PathVariable String imageName) {
        var resource = fileService.load(imageName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + imageName + "\"")
                .contentType(MediaType.IMAGE_PNG).body(resource);
    }
//    @GetMapping("/images/{imageName}")
//    public ResponseEntity<Resource> getUserImage(@PathVariable String imageName) {
//        var resource = fileService.load(imageName);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + imageName + "\"")
//                .contentType(MediaType.IMAGE_PNG).body(resource);
//    }


}
