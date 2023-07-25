package com.FinalProject.service.impl;

import com.FinalProject.exception.StudentNotFoundException;
import com.FinalProject.model.Student;
import com.FinalProject.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


//
@RequiredArgsConstructor
@Service
public class StudentServiceImpl {
    private final StudentRepository studentRepository;
//    private final StudentMapper studentMapper;
////    private final ModelMapper modelMapper;
//
//    //List
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
////    @Override
////    public Student updateStudent(UpdateStudentDto dto) {
////        return studentRepository
////                .findById(dto.getId()).map(student -> modelMapper.map(dto,Student.class))
////                .orElseThrow(StudentNotFoundException::new);
////    }
//
//        //View

        public Student findById (Long id){

            var optional = studentRepository.findById(id);

            if (optional.isEmpty()) throw new StudentNotFoundException();

            return optional.get();
        }
//
//
////Orders
//
//    }
}