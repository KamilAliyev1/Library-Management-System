package com.FinalProject.service.impl;

import com.FinalProject.dto.studentdto.CreateStudentDto;
import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.dto.studentdto.UpdateStudentDto;
import com.FinalProject.exception.StudentNotFoundException;
import com.FinalProject.mapper.StudentMapper;
import com.FinalProject.model.Student;
import com.FinalProject.repository.StudentRepository;
import com.FinalProject.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<StudentDto> getStudents() {
        List<Student> students = studentRepository.findAllByDeleteStatusFalseOrderByIDDesc();
        return students.stream()
                .map(studentMapper::mapStudentEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createStudent(CreateStudentDto studentDto) {
        Student student = studentMapper.mapCreateAuthorDtoToEntity(studentDto);
        student.setDeleteStatus(false);
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = fetchStudentIfExists(id);
        student.setDeleteStatus(true);
        studentRepository.save(student);
    }

    @Override
    public void updateStudent(Long id, UpdateStudentDto studentDto) {
        Student student = fetchStudentIfExists(id);
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setStudentFIN(studentDto.getStudentFIN());
        student.setFaculty(studentDto.getFaculty());

        studentRepository.save(student);
    }

    @Override
    public StudentDto getStudent(Long id) {
        return studentMapper.mapStudentEntityToDto(fetchStudentIfExists(id));
    }

    public Student fetchStudentIfExists(Long id) {
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student don't find with id: " + id));
    }
}
