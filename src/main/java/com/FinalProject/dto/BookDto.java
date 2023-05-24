package com.FinalProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private String name;
    private String isbn;
    private int stock;
    private String authorName;
    private String category;
    private String image;

    public BookDto(Long id, String name, String isbn, int stock, String authorName, String category) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.stock = stock;
        this.authorName = authorName;
        this.category = category;
    }
}
