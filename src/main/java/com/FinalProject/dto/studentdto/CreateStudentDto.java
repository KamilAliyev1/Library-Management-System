package com.FinalProject.dto.studentdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentDto {
    private String name;
    private String surname;
    private String faculty;
    private String studentFIN;
}
