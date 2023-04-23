package com.FinalProject.dto;


import com.FinalProject.model.Books;

import java.time.LocalDateTime;
import java.util.List;


public record OrderGETv1(

        Long id,
        Long studentId,

        List<Long> books,

        Boolean inProgress,

        LocalDateTime createdAt,

        LocalDateTime finishedAt,
        Boolean inDelay

) {
}
