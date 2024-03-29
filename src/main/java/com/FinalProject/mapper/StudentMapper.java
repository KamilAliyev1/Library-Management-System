package com.FinalProject.mapper;

import com.FinalProject.dto.CreateStudentDto;
import com.FinalProject.dto.StudentDto;
import com.FinalProject.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapper {
    public StudentDto mapStudentEntityToDto(Student student) {
        return StudentDto.builder()
                .id(student.getID())
                .name(student.getName())
                .surname(student.getSurname())
                .faculty(student.getFaculty())
                .studentFIN(student.getStudentFIN())
                .build();
    }

    public Student mapStudentToDtoEntity(StudentDto student) {
        return Student.builder()
                .ID(student.getId())
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

    public List<StudentDto> mapEntityListToResponseList(List<Student> students) {
        return students
                .stream()
                .map(this::mapStudentEntityToDto)
                .toList();
    }
}
