package com.FinalProject.mapper.studentMapper;

import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.dto.studentdto.StudentRequest;
import com.FinalProject.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, StudentDto.class, StudentRequest.class})
public interface StudentMapper {
    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "surname", source = "surname"),
            @Mapping(target = "faculty", source = "faculty"),
            @Mapping(target = "studentFIN",source = "studentFIN"),
            @Mapping(target = "deleteStatus",source = "deleteStatus")
    })
    Student mapDtoToEntity(StudentDto studentDto);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "surname", source = "surname"),
            @Mapping(target = "faculty", source = "faculty"),
            @Mapping(target = "studentFIN",source = "studentFIN"),
            @Mapping(target = "deleteStatus",source = "deleteStatus")
    })
    StudentDto mapEntityToDto(Student student);

    @Mappings({
            @Mapping(target = "ID", source = "ID"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "surname", source = "surname"),
            @Mapping(target = "faculty", source = "faculty"),
            @Mapping(target = "studentFIN",source = "studentFIN"),
            @Mapping(target = "deleteStatus",source = "deleteStatus")
    })
    Student mapDtoToEntity(StudentRequest studentRequest);
}
