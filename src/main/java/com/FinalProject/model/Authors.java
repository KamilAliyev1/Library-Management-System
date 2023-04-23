package com.FinalProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String fullName;
    @CreationTimestamp
    private LocalDate birthDate;

    private String nickName;
    private String email;
    private String address;
    @NotEmpty
    private String phone;
    private Boolean delete;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<AuthorBook> authorBooks = new ArrayList<>();
}
