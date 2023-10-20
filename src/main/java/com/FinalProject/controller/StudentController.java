package com.FinalProject.controller;

import com.FinalProject.dto.CreateStudentDto;
import com.FinalProject.dto.StudentDto;
import com.FinalProject.dto.UpdateStudentDto;
import com.FinalProject.service.StudentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.FinalProject.exception.ExceptionHandler.setExceptionMessage;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public String getStudents(Model model) {
        final List<StudentDto> students = studentService.getStudents();
        model.addAttribute("students", students);
        return "students/students";
    }

    @GetMapping("/search")
    public String searchStudents(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "surname", required = false) String surname,
            @RequestParam(name = "studentFin", required = false) String studentFin,
            Model model) {

        List<StudentDto> students = studentService.searchStudents(name, surname, studentFin);
        model.addAttribute("students", students);

        return "students/students";
    }

    @GetMapping("/add")
    public String createStudentPage(Model model, HttpServletRequest request) {
        setExceptionMessage(model, request);
        CreateStudentDto createStudentDto = new CreateStudentDto();
        model.addAttribute("student", createStudentDto);
        return "students/student-create";
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

    @GetMapping("/{id}/update")
    public String updateStudentPage(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        setExceptionMessage(model, request);
        StudentDto student = studentService.getStudent(id);
        model.addAttribute("student", student);
        return "students/student-update";
    }

    @PostMapping("/{id}/update")
    public String updateStudent(@PathVariable("id") Long id, @RequestBody UpdateStudentDto student) {
        studentService.updateStudent(id, student);
        return "redirect:/students";
    }
}
