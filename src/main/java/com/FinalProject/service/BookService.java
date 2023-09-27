package com.FinalProject.service;

import com.FinalProject.dto.BookDto;
import com.FinalProject.model.Book;
import com.FinalProject.request.BookRequest;

import java.util.List;

public interface BookService {

    void create(BookRequest bookRequest);

    void update(String isbn, BookRequest bookRequest);

    void delete(String isbn);

    BookDto findByIsbn(String isbn);

    List<BookDto> searchBooks(String isbn, Long categoryId, Long authorId);

    List<BookDto> findAll();

    boolean areAllBooksInStock(List<Long> id);

    void updateStockNumbersByIdIn(List<Long> ids, int amount);

    Book findById(Long id);


}
