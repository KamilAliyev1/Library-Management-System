//package com.FinalProject.controller;
//
//import com.FinalProject.dto.studentdto.CreateStudentDto;
//import com.FinalProject.dto.studentdto.StudentDto;
//import com.FinalProject.dto.studentdto.UpdateStudentDto;
//import com.FinalProject.model.Student;
//import com.FinalProject.service.StudentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/student")
//@RequiredArgsConstructor
//public class StudentController {
//    private final StudentService studentService;
//
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
//}
