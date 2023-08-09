package com.FinalProject.service;

import com.FinalProject.dto.BookDto;
import com.FinalProject.request.BookRequest;

import java.util.List;

public interface BookService {

    void create(BookRequest bookRequest);

    BookDto update(String isbn, BookRequest bookRequest);
  
    void delete(String isbn);
  
    BookDto findByIsbn(String isbn);

    List<BookDto> searchBooks(String isbn, Long categoryId, Long authorId);

    List<BookDto> findByAuthor(String authorFullName);
  
    List<BookDto> findAll();

    boolean areAllBooksInStock(List<Long> id);

    void updateStockNumbersByIdIn(List<Long> ids, int amount);
     List<BookDto> showBooksByCategoryName(String category);


}
