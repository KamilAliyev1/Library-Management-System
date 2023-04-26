package com.FinalProject.controller;

import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.model.Student;
import com.FinalProject.service.StudentService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    public List<StudentDto> getStudentList(){
        return studentService.getStudentList();
    }

    public Student findById(Long id) {
        return studentService.findById(id)
    }
}

