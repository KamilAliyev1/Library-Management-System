package com.FinalProject.dto.studentdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
//import org.mapstruct.Mapper;

//@Mapper
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    private String surname;
    private String faculty;
    private String studentFIN;
    private boolean deleteStatus;
}
