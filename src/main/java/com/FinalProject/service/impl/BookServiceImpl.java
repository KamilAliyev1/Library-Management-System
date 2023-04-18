package com.FinalProject.service.impl;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.BookRequest;
import com.FinalProject.exception.BookNotFoundException;
import com.FinalProject.model.Authors;
import com.FinalProject.model.Book;
import com.FinalProject.model.Category;
import com.FinalProject.repository.AuthorRepository;
import com.FinalProject.repository.BookRepository;
import com.FinalProject.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;


    private static List<BookDto> entityListToResponseList(List<Book> books) {
        return books.stream().map(book -> new BookDto(
                book.getId(), book.getName(), book.getIsbn(), book.getStock(), book.getAuthor().getFullName(), book.getCategory().getName())).toList();
    }

    private static BookDto entityToResponse(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .stock(book.getStock())
                .authorName(book.getAuthor().getFullName())
                .category(book.getCategory().getName())
                .isbn(book.getIsbn())
                .name(book.getName())
                .build();
    }

    public void create(List<Book> book) {

        var a = bookRepository.findByIsbn(book.stream().map(Book::getIsbn).collect(Collectors.toSet()));
        List<Book> newBooks = new LinkedList<>(a);


        first:
        for (var t : book) {
            for (var i : newBooks) {
                if (i.getIsbn().equals(t.getIsbn())) {
                    i.setStock(i.getStock() + t.getStock());
                    continue first;
                }
            }
            newBooks.add(t);

        }

        categoryRepository.saveAll(book.stream().map(Book::getCategory).collect(Collectors.toSet()));
        bookRepository.saveAll(newBooks);


    }

    public BookDto update(String isbn, BookRequest bookRequest) {
        Category bookCategory = Category.builder()
                .name(bookRequest.getCategory().getName())
                .build();
        Authors author = Authors.builder()
                .fullName(bookRequest.getAuthor().getFullName())
                .build();
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book not found with isbn : " + isbn));
        book.setCategory(bookCategory);
        book.setAuthor(author);
        book.setStock(bookRequest.getStock());
        book.setIsbn(bookRequest.getIsbn());
        book.setName(bookRequest.getName());
        categoryRepository.save(bookCategory);
        return entityToResponse(bookRepository.save(book));
    }

    @Transactional
    public void delete(String isbn) {
        bookRepository.findByIsbn(isbn).ifPresentOrElse(
                book -> bookRepository.deleteByIsbn(isbn),
                () -> {
                    throw new BookNotFoundException("Book not found with isbn : " + isbn);
                }
        );
    }

    public List<BookDto> findByCategory(String category) {

        Category bookCategory = new Category();
        bookCategory.setName(category);
        if (bookRepository.findByCategory(bookCategory).isEmpty())
            throw new BookNotFoundException("Book not found with category : " + bookCategory.getName());
        return entityListToResponseList(bookRepository.findByCategory(bookCategory));
    }

    public BookDto findByIsbn(String isbn) {
        return entityToResponse(bookRepository.findByIsbn(isbn).orElseThrow(
                () -> new BookNotFoundException("Book not found with isbn :" + isbn)));
    }

    public List<BookDto> findByAuthor(String authorFullName) {
        Authors author = new Authors();
        author.setFullName(authorFullName);
        List<Book> bookList = bookRepository.findByAuthor(author);
        if (bookList.isEmpty())
            throw new BookNotFoundException("Book not found with author : " + author.getFullName());

        return entityListToResponseList(bookList);
    }

    public List<BookDto> findAll() {
        return entityListToResponseList(bookRepository.findAll());
    }

    public boolean areAllBooksInStock(List<Long> id) {
        return bookRepository.areAllBooksInStock(id);
    }


    public void updateStockNumbersByIdIn(List<Long> ids,int amount){
        bookRepository.updateStockNumbersByIdIn(ids,amount);
    }


}
