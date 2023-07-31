package com.FinalProject.service;

import com.FinalProject.dto.studentdto.CreateStudentDto;
import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.dto.studentdto.UpdateStudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> getStudents();

    void createStudent(CreateStudentDto studentDto);

    void  deleteStudent(Long id);

    void updateStudent(Long id, UpdateStudentDto studentDto);

    StudentDto getStudent(Long id);
}
