package com.FinalProject.dto;

import com.FinalProject.model.Book;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorsDto {
    private Long id;
    private String fullName;
    private LocalDate birthDate;
    private String email;
    private String address;
    private String phone;
    private List<Book> books = new ArrayList<>();
}
