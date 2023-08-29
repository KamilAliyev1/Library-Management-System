package com.FinalProject.service.impl;

import com.FinalProject.dto.BookDto;
import com.FinalProject.exception.BookAlreadyFoundException;
import com.FinalProject.exception.BookNotFoundException;
import com.FinalProject.mapper.BookMapper;
import com.FinalProject.model.Book;
import com.FinalProject.repository.BookRepository;
import com.FinalProject.service.*;
import com.FinalProject.request.BookRequest;
import com.FinalProject.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Lazy
    private final CategoryService categoryService;
    private final FIleService fileService;
    private final BookMapper bookMapper;
    @Lazy
    private final AuthorService authorService;

    @Transactional
    public void create(BookRequest bookRequest) {
        var book = bookMapper.mapRequestToEntity(bookRequest);
        checkBookByIsbn(book);

        if (checkBookByIsbn(book).isEmpty()) {
            categoryService.setBookToCategory(bookRequest, book);
            authorService.setBookToAuthor(bookRequest, book);
            fileService.save(bookRequest.getFile());
            bookRepository.save(book);
        } else {
            throw new BookAlreadyFoundException("Book with isbn " + book.getIsbn() + " already exists");
        }
    }

    private Optional<Book> checkBookByIsbn(Book book) {
        return bookRepository.findByIsbn(book.getIsbn());
    }

    public void update(String isbn, BookRequest bookRequest) {
        Book book = bookRepository
                .findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with isbn : " + isbn));

        book.setName(bookRequest.getName());
        book.setIsbn(bookRequest.getIsbn());
        book.setImage(bookRequest.getFile().getOriginalFilename());
        book.setStock(bookRequest.getStock());

        categoryService.setBookToCategory(bookRequest, book);
        authorService.setBookToAuthor(bookRequest, book);
        fileService.save(bookRequest.getFile());
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(String isbn) {
        bookRepository.findByIsbn(isbn).ifPresentOrElse(book -> {
            bookRepository.deleteByIsbn(isbn);
//            fileService.deleteFile(book.getImage());
        }, () -> {
            throw new BookNotFoundException("Book not found with isbn : " + isbn);
        });
    }

    @Override
    public BookDto findByIsbn(String isbn) {
        return bookMapper.mapEntityToResponse((
                        bookRepository
                                .findByIsbn(isbn)
                                .orElseThrow(() -> new BookNotFoundException("Book not found with isbn :" + isbn))
                )
        );
    }

    public List<BookDto> searchBooks(String isbn, Long categoryId, Long authorId) {
        return bookMapper.mapEntityListToResponseList(
                bookRepository.searchBooks(isbn, categoryId, authorId)
        );
    }

    @Override
    public List<BookDto> findByAuthor(String authorFullName) {
        return null;
    }

    @Override
    public List<BookDto> findAll() {
        return bookMapper.mapEntityListToResponseList(bookRepository.findAllByOrderByIdDesc());
    }

    @Override
    public boolean areAllBooksInStock(List<Long> id) {
        return bookRepository.areAllBooksInStock(id);
    }

    @Override
    public void updateStockNumbersByIdIn(List<Long> books, int i) {
        bookRepository.updateStockNumbersByIdIn(books, i);
    }
}
