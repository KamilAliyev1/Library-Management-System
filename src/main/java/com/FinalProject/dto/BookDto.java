package com.FinalProject.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookDto {
    private Long id;
    private String name;
    private String category;
}
