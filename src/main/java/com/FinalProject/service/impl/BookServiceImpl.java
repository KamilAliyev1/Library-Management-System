package com.FinalProject.service.impl;

import com.FinalProject.dto.BookDto;
import com.FinalProject.exception.BookAlreadyFoundException;
import com.FinalProject.exception.BookNotFoundException;
import com.FinalProject.exception.StockNotEnoughException;
import com.FinalProject.mapper.BookMapper;
import com.FinalProject.model.Book;
import com.FinalProject.repository.BookRepository;
import com.FinalProject.request.BookRequest;
import com.FinalProject.service.AuthorService;
import com.FinalProject.service.BookService;
import com.FinalProject.service.CategoryService;
import com.FinalProject.service.FIleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Slf4j
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
        checkBookByIsbn(book).ifPresentOrElse(
                book1 -> {
                    throw new BookAlreadyFoundException("Book with isbn " + book.getIsbn() + " already exists");
                }, () -> {
                    categoryService.setBookToCategory(bookRequest, book);
                    authorService.setBookToAuthor(bookRequest, book);
                    fileService.save(bookRequest.getFile());
                    bookRepository.save(book);
                });
    }

    private Optional<Book> checkBookByIsbn(Book book) {
        return bookRepository.findByIsbn(book.getIsbn());
    }

    public void update(String isbn, BookRequest bookRequest) {
        bookRepository.findByIsbn(isbn).ifPresentOrElse(book -> {
            book.setName(bookRequest.getName());
            book.setIsbn(bookRequest.getIsbn());
            book.setImage(bookRequest.getFile().getOriginalFilename());
            book.setStock(bookRequest.getStock());
            categoryService.setBookToCategory(bookRequest, book);
            authorService.setBookToAuthor(bookRequest, book);
            fileService.save(bookRequest.getFile());
            bookRepository.save(book);
        }, () -> {
            throw new BookNotFoundException("Book not found with isbn: " + isbn);
        });
    }

    @Override
    @Transactional
    public void delete(String isbn) {
        bookRepository.findByIsbn(isbn).
                ifPresentOrElse
                        (
                                book ->
                                        bookRepository.deleteByIsbn(isbn),

                                () -> {
                                    throw new BookNotFoundException("Book not found with isbn : " + isbn);
                                });
    }

    @Override
    public BookDto findByIsbn(String isbn) {
        return bookMapper.mapEntityToResponse((bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book not found with isbn :" + isbn))));
    }

    public List<BookDto> searchBooks(String isbn, Long categoryId, Long authorId) {
        return bookMapper.mapEntityListToResponseList(bookRepository.searchBooks(isbn, categoryId, authorId));
    }

    @Override
    public List<BookDto> findAll() {
        return bookMapper.mapEntityListToResponseList(bookRepository.findAllByOrderByIdDesc());
    }

    @Override
    public void areAllBooksInStock(List<Long> id) {
        List<Book> books = bookRepository.findAllById(id);
        if(id.size()!=books.size()){
            for (int i = 0; i < id.size(); i++) {
                if(
                        !books.contains(
                        Book.builder().id(id.get(i)).build()
                )
                )throw new BookNotFoundException("book not founded with id:"+id.get(i));
            }
        }
        for (var i:books) {
            if(i.getStock()==0)throw new StockNotEnoughException("There is not enough stock for the book: "+i.getName());
        }
    }

    @Override
    public void updateStockNumbersByIdIn(List<Long> books, int i) {
        bookRepository.updateStockNumbersByIdIn(books, i);
    }


    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("book not founded with id:" + id));
    }

}
