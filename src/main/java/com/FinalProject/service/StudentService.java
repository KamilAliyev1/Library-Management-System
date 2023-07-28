package com.FinalProject.service;

import com.FinalProject.dto.studentdto.CreateStudentDto;
import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.dto.studentdto.UpdateStudentDto;
import com.FinalProject.model.Student;

import java.util.List;

public interface StudentService {
    List<StudentDto> getStudents();

    Long createStudent(CreateStudentDto studentDto);

    void  deleteStudentById(Long id);

    Student updateStudent(UpdateStudentDto dto);

    Student findById(Long id);
}
