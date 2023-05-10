package com.FinalProject.service.impl;

import com.FinalProject.dto.studentdto.CreateStudentDto;
import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.dto.studentdto.UpdateStudentDto;
import com.FinalProject.exception.StudentAlreadyExistsException;
import com.FinalProject.exception.StudentNotFoundException;
import com.FinalProject.mapper.studentMapper.StudentMapper;
import com.FinalProject.model.Student;
import com.FinalProject.repository.StudentRepository;
import com.FinalProject.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final ModelMapper modelMapper;

    //List
    @Override
    public List<StudentDto> getStudentList() {

        return studentRepository.findAll().stream()
                .map(studentMapper::mapEntityToDto).collect(Collectors.toList());
    }

    //Create
    @Override
    public Long createStudent(CreateStudentDto createStudentDto) {
        var student = studentRepository.findByStudentFIN(createStudentDto
                .getStudentFIN()).isPresent();
        if (student) {
            throw new StudentAlreadyExistsException(
                    "Student already exists with " + createStudentDto.getStudentFIN() + "fin");
        } else {
            studentRepository.save(modelMapper.map(createStudentDto, Student.class));
            return studentRepository.findByStudentFIN(createStudentDto.getStudentFIN()).get().getID();
        }
    }

    //Delete
    @Override
    public void deleteStudentById(Long id) {
        Optional<Student> byId = studentRepository.findById(id);
       if (byId.isPresent()){
           byId.get().setDeleteStatus(true);
           studentRepository.save(byId.get());
       }
        else throw new StudentNotFoundException("Unfortunately Student is not exist");
    }

    //Update
    @Override
    public Student updateStudent(UpdateStudentDto dto) {
        return studentRepository
                .findById(dto.getId()).map(student -> modelMapper.map(dto, Student.class))
                .orElseThrow(StudentNotFoundException::new);
    }

    //View
    @Override
    public Student findById(Long id) {

        var optional = studentRepository.findById(id);

        if (optional.isEmpty()) throw new StudentNotFoundException();

        return optional.get();
    }


//Orders

}
