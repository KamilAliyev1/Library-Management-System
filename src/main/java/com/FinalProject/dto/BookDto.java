package com.FinalProject.dto;

import lombok.*;
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
    private Integer stock;
    private String image;
    private String category;
    private String authorName;
    private Long authorId;
    private Long categoryId;
    private MultipartFile file;
}
