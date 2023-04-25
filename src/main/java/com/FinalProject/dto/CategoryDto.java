package com.FinalProject.dto;

import com.FinalProject.model.Book;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    private String name;

    private Set<Book> books = new HashSet<>();
}
