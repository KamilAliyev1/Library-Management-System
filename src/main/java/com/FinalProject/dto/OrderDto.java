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
    private Long id;
    private Long studentId;
    private String studentFullName;
    private String studentFin;
    private List<String> books;
    private Boolean inProgress;
    private LocalDate createdAt;
    private LocalDate finishedAt;
    private Boolean inDelay;
}