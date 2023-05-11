package com.FinalProject.dto.studentdto;

import enums.Faculty;
import lombok.Data;

@Data
public class CreateStudentDto {
    private String name;
    private String surname;
    private Faculty faculty;
    private String studentFIN;
}
