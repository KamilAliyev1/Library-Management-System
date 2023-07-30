package com.FinalProject.dto.studentdto;

import com.FinalProject.enums.Faculty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;

@AllArgsConstructor
@NoArgsConstructor
@Mapper
@Builder
public class StudentDto {
    private Long id;
    private String name;
    private String surname;
    private String faculty;
    private String studentFIN;
    private boolean deleteStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getStudentFIN() {
        return studentFIN;
    }

    public void setStudentFIN(String studentFIN) {
        this.studentFIN = studentFIN;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
