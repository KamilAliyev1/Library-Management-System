package com.FinalProject.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String name;
    private String isbn;
    private int stock;
    private Long authorId;
    private Long categoryId;
    private MultipartFile file;
}
