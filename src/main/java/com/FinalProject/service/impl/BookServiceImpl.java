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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private static List<Book> requestListToEntityList(List<BookRequest> bookRequests) {
        return bookRequests.stream().map(bookRequest ->
                new Book(bookRequest.getIsbn(), bookRequest.getName(), bookRequest.getStock(),
                        Authors.builder()
                                .fullName(bookRequest.getFullName())
                                .build(), Category.builder()
                        .name(bookRequest.getCategory())
                        .build()
                )).toList();

    }

    @Transactional
    public List<BookDto> create(List<BookRequest> bookRequests) {

        List<Book> books = requestListToEntityList(bookRequests);
        List<Book> newBooks = new ArrayList<>();
        for (Book book : books) {
            Optional<Category> category = categoryRepository.findByName(book.getCategory().getName());
            if (category.isPresent()) {
                book.setCategory(category.get());
            } else {
                Category newCategory = new Category();
                newCategory.setName(book.getCategory().getName());
                categoryRepository.save(newCategory);
                book.setCategory(newCategory);
            }
            newBooks.add(book);
        }
        return entityListToResponseList(bookRepository.saveAll(newBooks));
    }

    public BookDto update(String isbn, BookRequest bookRequest) {
        Category bookCategory = Category.builder()
                .name(bookRequest.getCategory())
                .build();
        Authors author = Authors.builder()
                .fullName(bookRequest.getFullName())
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
        if (bookRepository.findByCategory(category).isEmpty())
            throw new BookNotFoundException("Book not found with category :  " + category);
        return entityListToResponseList(bookRepository.findByCategory(category));
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


    public void updateStockNumbersByIdIn(List<Long> ids, int amount) {
        bookRepository.updateStockNumbersByIdIn(ids, amount);
    }


}
