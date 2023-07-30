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

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
//    private final ModelMapper modelMapper;

//    public List<CategoryDto> findAllCategories() {
//
//        List<Category> categories = categoryRepository.findAll();
//        return categories.stream()
//                .map(categoryMapper::categoryToCategoryDto)
//                .collect(Collectors.toList());
//    }

    public List<StudentDto> getStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(studentMapper::mapStudentDtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long createStudent(CreateStudentDto studentDto) {
        return null;
    }

    @Override
    public void deleteStudentById(Long id) {

    }

    @Override
    public Student updateStudent(UpdateStudentDto dto) {
        return null;
    }

    //List
//    @Override
//    public List<StudentDto> getStudentList() {
//
//        return studentRepository.findAll().stream()
//                .map(studentMapper::mapEntityToDto).collect(Collectors.toList());
//    }
//
//    //Create
//    @Override
//    public Long createStudent(CreateStudentDto createStudentDto) {
//        var student = studentRepository.findByStudentFIN(createStudentDto
//                .getStudentFIN()).isPresent();
//        if (student) {
//            new StudentAlreadyExistsException(
//                    "Student already exists with " + createStudentDto.getStudentFIN() + "fin");
////        } else studentRepository.save(modelMapper.map(createStudentDto, Student.class));
//            return studentRepository.findByStudentFIN(createStudentDto.getStudentFIN()).get().getID();
//        }
//
//        //Delete
//        @Override
//        public void deleteStudentById (Long id){
//            Optional<Student> byId = studentRepository.findById(id);
//            byId.get().setDeleteStatus(true);
//            studentRepository.save(byId.get());
//        }
//
//        //Update
//    @Override
//    public Student updateStudent(UpdateStudentDto dto) {
//        return studentRepository
//                .findById(dto.getId()).map(student -> modelMapper.map(dto,Student.class))
//                .orElseThrow(StudentNotFoundException::new);
//    }
//
//        //View
//
        public Student findById (Long id){

            var optional = studentRepository.findById(id);

            if (optional.isEmpty()) throw new StudentNotFoundException();

            return optional.get();
        }


//Orders

}
