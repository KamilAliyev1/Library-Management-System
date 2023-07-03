package com.FinalProject.dto.studentdto;

import com.FinalProject.enums.Faculty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;

@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class StudentDto {
    private String name;
    private String surname;
    private Faculty faculty;
    private String studentFIN;
    private boolean deleteStatus;

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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
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
