package com.FinalProject.dto;


import java.time.LocalDate;
import java.util.List;


public record OrderDto(

        Long id,
        Long studentId,

        List<Long> books,

        Boolean inProgress,

        LocalDate createdAt,

        LocalDate finishedAt,
        Boolean inDelay

) {

}
