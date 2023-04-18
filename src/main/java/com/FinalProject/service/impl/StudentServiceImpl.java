package com.FinalProject.service.impl;

import com.FinalProject.dto.StudentDto;
import com.FinalProject.model.Student;
import com.FinalProject.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl {
    private final StudentRepository studentRepository;

//List
    public List<Student> getStudentList(){
        return studentRepository.getStudents();
    }
//Create
    public void create(StudentDto studentDto){
        Student student=Student.builder().name(studentDto.getName()).build();
        studentRepository.save(student);
    }
//Update
//View
//Delete
//Orders
}
