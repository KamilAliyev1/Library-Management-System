package com.FinalProject.dto;

import com.FinalProject.model.Books;
import com.FinalProject.model.Student;

import java.time.LocalDateTime;
import java.util.List;

public record OrderGETv1(

        Long id,
        List<Books> books,
        Student student,

        Boolean inProgress,

        LocalDateTime createdAt,

        LocalDateTime finishedAt

) {
}
