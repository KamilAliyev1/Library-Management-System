package com.FinalProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity(name = "Book")
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 13)
    @Size(min = 13, max = 13, message = "ISBN must be 13")
    private String isbn;

    private String name;
    private Integer stock;

    private Boolean deleteStatus;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Authors author;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    private String image;

    public Book(String isbn, String name, int stock, Authors author, Category category) {
        this.isbn = isbn;
        this.name = name;
        this.stock = stock;
        this.author = author;
        this.category = category;
    }

}
