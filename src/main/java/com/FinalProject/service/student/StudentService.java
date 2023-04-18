package com.FinalProject.service;

import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudentList();
    void create(StudentDto studentDto);
    void  deleteStudentById(Long id);
    Student update(StudentDto studentDto,Long id);

}
