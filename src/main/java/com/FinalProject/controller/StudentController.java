package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.studentdto.CreateStudentDto;
import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.dto.studentdto.UpdateStudentDto;
import com.FinalProject.model.Student;
import com.FinalProject.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImpl studentService;
    @GetMapping("/add")
    public String studentForm(CreateStudentDto studentRequest) {
        return "student-create";
    }

    @GetMapping("/getAll")
    public List<StudentDto> getStudentList() {
        return studentService.getStudentList();
    }
    @GetMapping
    public String findAll(Model model) {
        final var students = studentService.getStudentList();
        model.addAttribute("students", students);
        return "student-list";
    }

    @PostMapping
    public String createStudent(@ModelAttribute("studentRequest") CreateStudentDto studentRequest, Model model) {
        if (studentRequest==null){
            return "student-create";
        }
        else {
            studentService.createStudent(studentRequest);
            model.addAttribute("studentRequest", studentService.getStudentList());
            return "redirect:/student";
        }

    }

    @PutMapping("/delete")
    public void deleteStudentById(Long id) {
        studentService.deleteStudentById(id);
    }

    @PutMapping("/update")
    public Student updateStudent(UpdateStudentDto updateStudentDto) {
        return studentService.updateStudent(updateStudentDto);
    }

    @GetMapping("/getById")
    public Student findById(Long id) {
        return studentService.findById(id);
    }
}
