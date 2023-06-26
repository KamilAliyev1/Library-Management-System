package com.FinalProject.repository;

import com.FinalProject.model.Authors;
import com.FinalProject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT u from Book u  join Authors a on u.id = a.id where u.id=:authorId")
    List<Book> findBooksByAuthorId(Long authorId);

    @Query(value = "select b from Book b where b.isbn in :isbn")
    Set<Book> findByIsbn(Set<String> isbn);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN false ELSE true END FROM Book b WHERE b.id IN :id AND b.stock = 0")
    boolean areAllBooksInStock(@Param("id") List<Long> id);
    @Query("SELECT CASE WHEN COUNT(b)<>:size THEN false ELSE true END FROM Book b WHERE b.id in :id")
    boolean areAllBooksInTable(@Param("id") List<Long> id,@Param("size") Long size);

    @Query("select b from Book b where b.category.name ilike :category")
    List<Book> findByCategory(String category);

    void deleteByIsbn(String isbn);


    List<Book> findByAuthor(Authors author);

    Optional<Book> findByIsbn(String isbn);
    @Modifying
    @Query("UPDATE Book b SET b.stock=b.stock+:i where b.id in :ids")
    int updateStockNumbersByIdIn(@Param("ids") List<Long> ids, int i);
}
/* 1)SELECT CASE
    WHEN COUNT(b)!=COUNT(ids) THEN 'false'
    ELSE 'true'
    FROM books
    WHERE id in ids


    2)select case
    when count(s)>0 Then false
    else true
    from books
    where id in ids and stock = 0


   SELECT CASE
    WHEN
    (SELECT CASE
    WHEN COUNT(b)<>COUNT(d) THEN false
    ELSE true END
    FROM b left join :id d on b.id in d
    WHERE b.id in :id)
    AND
    (select case
    when count(b)>0 THEN false
    else true END
    from b
    where b.id in :id and b.stock = 0)
    THEN true
    ELSE false
    END
    FROM Book b;
*/