package com.FinalProject.service.impl;

import com.FinalProject.dto.StudentDto;
import com.FinalProject.model.Student;
import com.FinalProject.repository.StudentRepository;
import com.FinalProject.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    //List
    @Override
    public List<Student> getStudentList() {
        return studentRepository.getStudents();
    }

    //Create
    @Override
    public void create(StudentDto studentDto) {
        Student student = Student.builder().name(studentDto.getName()).build();
        studentRepository.save(student);
    }

    //Delete
    @Override
    public void deleteStudentById(Long id) {
        Optional<Student> byId = studentRepository.findById(id);
        byId.get().setDeleteStatus(true);
        studentRepository.save(byId.get());

    }

    //Update
    @Override
    public Student update(StudentDto studentDto, Long id) {
        return studentRepository.findById(id).map(student ->
                Student.builder()
                        .name(studentDto.getName())
                        .surname(studentDto.getSurname())
                        .deleteStatus(studentDto.isDeleteStatus())).orElseThrow().build();
    }


//View


//Orders
}
