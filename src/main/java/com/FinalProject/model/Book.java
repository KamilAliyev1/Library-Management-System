package com.FinalProject.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "isbn")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String isbn;
    private String name;
    private int stock;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Authors author;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void setBookCategory(Category bookCategory) {
        bookCategory.getBook().add(this);
        this.category = bookCategory;
    }


}
