package com.FinalProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    private String name;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "category")
    private Set<Book> book = new HashSet<>();

}
