package com.FinalProject.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String fullName;
    @CreationTimestamp
    private LocalDate birthDate;
    private String nickName;
    private String email;
    private String address;
    private String phone;
    private Boolean delete;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> authorBooks = new ArrayList<>();
}
