package com.FinalProject.dto;

import com.FinalProject.model.Books;
import com.FinalProject.model.Student;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderPOSTv1(
        @NotNull
        Student student,
        @NotEmpty
        List<Books> books
) {

}
