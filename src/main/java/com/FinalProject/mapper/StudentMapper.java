package com.FinalProject.mapper;

import com.FinalProject.dto.studentdto.CreateStudentDto;
import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto mapStudentDtoToEntity(Student student) {
        return StudentDto.builder()
                .id(student.getID())
                .name(student.getName())
                .surname(student.getSurname())
                .faculty(student.getFaculty())
                .studentFIN(student.getStudentFIN())
                .build();
    }

    public Student mapCreateAuthorDtoToEntity(CreateStudentDto studentDto) {
        return Student.builder()
                .name(studentDto.getName())
                .surname(studentDto.getSurname())
                .studentFIN(studentDto.getStudentFIN())
                .faculty(studentDto.getFaculty())
                .build();
    }
}
