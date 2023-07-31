package com.FinalProject.controller;

import com.FinalProject.dto.studentdto.CreateStudentDto;
import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.service.StudentService;
import jakarta.validation.Valid;
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

    @GetMapping("/add")
    public String createStudentPage(Model model) {
        CreateStudentDto createStudentDto = new CreateStudentDto();
        model.addAttribute("student", createStudentDto);
        return "student-create";
    }

    @PostMapping
    public String createStudent(@ModelAttribute("student") CreateStudentDto studentDto, @Valid Model model) {
        studentService.createStudent(studentDto);
        model.addAttribute("students", studentService.getStudents());
        return "redirect:/students";
    }

    @GetMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, Model model) {
        studentService.deleteStudent(id);
        model.addAttribute("students", studentService.getStudents());
        return "redirect:/students";
    }
}
