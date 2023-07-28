package com.FinalProject.controller;

import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public String findAll(Model model) {
        final List<StudentDto> students = studentService.getStudents();
        model.addAttribute("students", students);
        System.out.println(Arrays.asList(students));
        return "students";
    }

//    @GetMapping("/getAll")
//    public List<StudentDto> getStudentList() {
//        return studentService.getStudentList();
//    }
//
//    @PostMapping("/create")
//    public Long createStudent(CreateStudentDto studentDto) {
//        return studentService.createStudent(studentDto);
//
//    }
//
//    @PutMapping("/delete")
//    public void deleteStudentById(Long id) {
//        studentService.deleteStudentById(id);
//    }
//
//    @PutMapping("/update")
//    public Student updateStudent(UpdateStudentDto updateStudentDto) {
//        return studentService.updateStudent(updateStudentDto);
//    }
//
//    @GetMapping("/getById")
//    public Student findById(Long id) {
//        return studentService.findById(id);
//    }
}
