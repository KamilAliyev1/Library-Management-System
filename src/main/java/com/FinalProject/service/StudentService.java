package com.FinalProject.service;

import com.FinalProject.dto.CreateStudentDto;
import com.FinalProject.dto.StudentDto;
import com.FinalProject.dto.UpdateStudentDto;
import com.FinalProject.model.Student;

import java.util.List;

public interface StudentService {
    List<StudentDto> getStudents();

    List<StudentDto> searchStudents(String name, String surname, String studentFin);

    void createStudent(CreateStudentDto studentDto);

    void deleteStudent(Long id);

    void updateStudent(Long id, UpdateStudentDto studentDto);

    StudentDto getStudent(Long id);

    Student findById(Long id);
}
