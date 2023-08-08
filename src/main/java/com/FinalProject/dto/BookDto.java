package com.FinalProject.dto;

import lombok.*;

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
}
