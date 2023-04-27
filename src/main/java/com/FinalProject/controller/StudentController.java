package com.FinalProject.controller;

import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.model.Student;
import com.FinalProject.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/getAll")
    public List<StudentDto> getStudentList() {
        return studentService.getStudentList();
    }

    @GetMapping("/getById")
    public Student findById(Long id) {
        return studentService.findById(id);
    }

    @PostMapping("/add")
    public void createStudent(StudentDto studentDto) {
        studentService.createStudent(studentDto);
    }

    @PutMapping("/update")
    public Student updateStudent(StudentDto studentDto, Long id) {
        return studentService.updateStudent(studentDto, id);
    }
}

