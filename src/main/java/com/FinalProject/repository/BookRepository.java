package com.FinalProject.repository;

import com.FinalProject.model.Authors;
import com.FinalProject.model.Book;
import com.FinalProject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT u from Book u  join AuthorBook a on u.id= a.book.id where u.id=:authorId")
    List<Book> findBooksByAuthorId(Long authorId);

    @Query(value = "select b from Book b where b.isbn in :isbn")
    Set<Book> findByIsbn(Set<String> isbn);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN false ELSE true END FROM Book b WHERE b.id IN :id AND b.stock = 0")
    boolean areAllBooksInStock(@Param("isbns") List<Long> id);

    List<Book> findByCategory(Category bookCategory);

    void deleteByIsbn(String isbn);


    List<Book> findByAuthor(Authors author);

    Optional<Book> findByIsbn(String isbn);

}
