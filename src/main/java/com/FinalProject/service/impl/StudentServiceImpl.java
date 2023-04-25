package com.FinalProject.service.impl;

import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.exception.StudentNotFoundException;
import com.FinalProject.mapper.studentMapper.StudentMapper;
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
    private final StudentMapper studentMapper;

    //List
    @Override
    public List<StudentDto> getStudentList() {

        return studentRepository.getStudents().stream()
                .map(studentMapper::mapEntityToDto).collect(Collectors.toList());
    }

    //Create
    @Override
    public void createStudent(StudentDto studentDto) {
        studentRepository.save(studentMapper.mapDtoToEntity(studentDto));
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
    public Student updateStudent(StudentDto studentDto, Long id) {
        return studentRepository
                .findById(id).map(student -> studentMapper.mapDtoToEntity(studentDto))
                .orElseThrow();
    }

    @Override
    public Student findById(Long id) {

        var optional = studentRepository.findById(id);

        if(optional.isEmpty()) throw new StudentNotFoundException();

        return optional.get();
    }

    //View


//Orders

}
