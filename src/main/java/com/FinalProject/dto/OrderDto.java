package com.FinalProject.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    Long id;
    Long studentId;
    String studentFullName;
    String studentFin;
    List<String> books;
    Boolean inProgress;
    LocalDate createdAt;
    LocalDate finishedAt;
    Boolean inDelay;
}