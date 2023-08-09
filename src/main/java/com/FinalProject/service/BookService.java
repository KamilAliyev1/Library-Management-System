package com.FinalProject.service;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.BookRequest;

import java.util.List;

public interface BookService {

    List<BookDto> create(List<BookRequest> bookRequests);

    BookDto update(String isbn, BookRequest bookRequest);

    void delete(String isbn);

    List<BookDto> findByCategory(String category);

    BookDto findByIsbn(String isbn);

    List<BookDto> searchBooks(String isbn, Long categoryId, Long authorId);

    List<BookDto> findByAuthor(String authorFullName);

    List<BookDto> findAll();

    boolean areAllBooksInStock(List<Long> id);

    void updateStockNumbersByIdIn(List<Long> ids, int amount);

}
