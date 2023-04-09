package com.FinalProject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "author_book")
public class AuthorBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Authors author;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books book;

    // constructors, getters, and setters
}
