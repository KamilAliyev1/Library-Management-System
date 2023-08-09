package com.FinalProject.service.impl;

import com.FinalProject.dto.BookDto;
import com.FinalProject.exception.BookAlreadyFoundException;
import com.FinalProject.exception.BookNotFoundException;
import com.FinalProject.mapper.BookMapper;
import com.FinalProject.model.Book;
import com.FinalProject.repository.BookRepository;
import com.FinalProject.request.BookRequest;
import com.FinalProject.service.AuthorService;
import com.FinalProject.service.BookService;
import com.FinalProject.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
    @Override
    public void create(BookRequest bookRequest) {
        var book = bookMapper.requestToEntity(bookRequest);
        checkBookByIsbn(book);
        categoryService.setBookToCategory(bookRequest, book);
        authorService.setBookToAuthor(bookRequest, book);
        fileService.save(bookRequest.getFile());
        bookRepository.save(book);
    }


    private void checkBookByIsbn(Book book) {
        bookRepository.findByIsbn(book.getIsbn()).ifPresent(book1 -> {
            throw new BookAlreadyFoundException("Book found with isbn:" + book1.getIsbn());
        });
    }

    public BookDto update(String isbn, BookRequest bookRequest) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book not found with isbn : " + isbn));
        book.setName(bookRequest.getName());
        book.setIsbn(bookRequest.getIsbn());
        book.setImage(bookRequest.getFile().getOriginalFilename());
        book.setStock(bookRequest.getStock());
        categoryService.setBookToCategory(bookRequest, book);
        authorService.setBookToAuthor(bookRequest, book);
        fileService.save(bookRequest.getFile());
        bookRepository.save(book);
        return bookMapper.entityToDto(bookRepository.save(book));
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
        return bookMapper.entityToDto(bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book not found with isbn : " + isbn)));
    }

    @Override
    public List<BookDto> findAll() {
        return bookMapper.entityListToDtoList(bookRepository.findAll());
    }

    @Override
    public boolean areAllBooksInStock(List<Long> id) {
        return bookRepository.areAllBooksInStock(id);
    }

    @Override
    public void updateStockNumbersByIdIn(List<Long> books, int i) {
        bookRepository.updateStockNumbersByIdIn(books, i);
    }

    @Override
    public List<BookDto> showBooksByCategoryName(String category) {
        if (bookRepository.findByCategory(category).isEmpty())
            throw new BookNotFoundException("Book not found with category :  " + category);
        List<Book> book = bookRepository.findByCategory(category);
        return bookMapper.entityListToDtoList(bookRepository.findByCategory(category));
    }


}
