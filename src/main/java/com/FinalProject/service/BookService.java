package com.FinalProject.service;

import com.FinalProject.dto.BookDto;
import com.FinalProject.model.Book;
import com.FinalProject.dto.BookRequest;

import java.util.List;

public interface BookService {

    void create(BookRequest bookRequest);

    void update(String isbn, BookRequest bookRequest);

    void delete(String isbn);

    BookDto findByIsbn(String isbn);

    List<BookDto> searchBooks(String isbn, Long categoryId, Long authorId);

    List<BookDto> findAll();

    void areAllBooksInStock(List<Long> id);

    void updateStockNumbersByIdIn(List<Long> ids, int amount);

    Book findById(Long id);

    void checkBooksIsDeleted(List<Long> ids);

    List<BookDto> getAllBooks();

}
