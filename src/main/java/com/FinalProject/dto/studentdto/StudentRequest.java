package com.FinalProject.dto.studentdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private Long ID;
    private String name;
    private String surname;
    private String faculty;
    private String studentFIN;
    private boolean deleteStatus;
}
