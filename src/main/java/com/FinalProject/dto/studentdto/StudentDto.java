package com.FinalProject.dto.studentdto;

import enums.Faculty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Mapper
public class StudentDto {
    private String name;
    private String surname;
    private Faculty faculty;
    private String studentFIN;
    private boolean deleteStatus;
}
