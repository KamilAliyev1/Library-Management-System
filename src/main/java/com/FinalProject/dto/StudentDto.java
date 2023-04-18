package com.FinalProject.dto;

import enums.Faculty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Mapper
public class StudentDto {
    private String name;
    private String surname;
    private Faculty faculty;
    private String studentFIN;
}
