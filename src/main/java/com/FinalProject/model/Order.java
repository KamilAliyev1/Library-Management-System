package com.FinalProject.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Set;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "ID")
@EqualsAndHashCode(of = "ID")
@Entity(name = "order")
@Table(name = "orders", uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id"})})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ID;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "orders_books",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    Set<Book> books;


    @JoinColumn(name = "student_id", nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH)
    Student student;

    @Column(nullable = false)
    Boolean inProgress;

    @Column(nullable = false)
    Boolean inDelay;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDate createdAt;

    LocalDate finishedAt;


    @PrePersist
    private void init() {
        this.setCreatedAt(LocalDate.now());
        this.setInDelay(false);
        this.setInProgress(true);
    }


    public Boolean getInDelay() {
        return inDelay;
    }

    public void setInDelay(Boolean inDelay) {
        this.inDelay = inDelay;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDate finishedAt) {
        this.finishedAt = finishedAt;
    }

    public void addBook(Book books) {
        this.books.add(books);
    }

}
