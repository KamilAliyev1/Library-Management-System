package com.FinalProject.service;

import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.model.Student;

import java.util.List;

public interface StudentService {
    List<StudentDto> getStudentList();
    void createStudent(StudentDto studentDto);
    void  deleteStudentById(Long id);
    Student updateStudent(StudentDto studentDto, Long id);

    Student findById(Long id);


//View


//Orders
}
