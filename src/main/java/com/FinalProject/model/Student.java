package com.FinalProject.model;


import enums.Faculty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "sur_name", nullable = false)
    private String surname;
    @Column(name = "faculty",nullable = false)
    private Faculty faculty;
    @Column(name = "FIN", length = 7, unique = true, nullable = false)
    private String studentFIN;
    @Column(updatable = false)
    private boolean deleteStatus;


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
