package com.FinalProject.repository;

import com.FinalProject.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
    @Query("SELECT u from Books u  join AuthorBook a on u.id=a.book.id where u.id=:authorId")
    List<Books> findBooksByAuthorId(Long authorId);
}
