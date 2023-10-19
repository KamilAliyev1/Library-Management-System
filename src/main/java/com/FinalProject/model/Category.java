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
    @Column(nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
    private Set<Book> book = new HashSet<>();
    @Column(nullable = false)
    private boolean deleteStatus;
}
