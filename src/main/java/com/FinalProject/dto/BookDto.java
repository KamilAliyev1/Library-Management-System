package com.FinalProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String name;
    private String isbn;
    private int stock;
    private String authorName;
    private String category;
    private String image;
    private Long authorId;
    private Long categoryId;
    private MultipartFile file;
}
