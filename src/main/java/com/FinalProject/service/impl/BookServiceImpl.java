package com.FinalProject.service.impl;

import com.FinalProject.dto.BookDto;
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
        categoryService.setBookToCategory(bookRequest, book);
        authorService.setBookToAuthor(bookRequest, book);
        fileService.save(bookRequest.getFile());
        bookRepository.save(book);
    }

    private void checkBookByIsbn(Book book) {
        bookRepository.findByIsbn(book.getIsbn()).ifPresent(book1 -> {
            throw new RuntimeException("Book not found with isbn:" + book1.getIsbn());
        });
    }

    public void update(String isbn, BookRequest bookRequest) {
        Book book = bookRepository
                .findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with isbn : " + isbn));


//        var category = categoryRepository.findById(bookRequest.getCategoryId()).orElseThrow(() ->
//                new CategoryNotFoundException("Not found Category such id: " + bookRequest.getCategoryId())
//        );
//        var author = authorRepository.findById(bookRequest.getAuthorId()).orElseThrow(() ->
//                new AuthorsNotFoundException("Not found Author such id = " + bookRequest.getAuthorId())
//        );

        book.setName(bookRequest.getName());
        book.setIsbn(bookRequest.getIsbn());
        book.setImage(bookRequest.getFile().getOriginalFilename());
        book.setStock(bookRequest.getStock());

        categoryService.setBookToCategory(bookRequest, book);
        authorService.setBookToAuthor(bookRequest, book);
        fileService.save(bookRequest.getFile());
        bookRepository.save(book);

//        book.setCategory(category);
//        book.setAuthor(author);
//        book.setStock(bookRequest.getStock());
//        book.setIsbn(bookRequest.getIsbn());
//        book.setName(bookRequest.getName());
//        book.setImage(bookRequest.getFile().getOriginalFilename());
//        save(bookRequest.getFile());
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

//    public List<BookDto> findByAuthor(String authorFullName) {
//        Authors author = new Authors();
//        author.setFullName(authorFullName);
//        List<Book> bookList = bookRepository.findByAuthor(author);
//        if (bookList.isEmpty()) throw new BookNotFoundException("Book not found with author : " + author.getFullName());
//
//        return bookMapper.mapEntityListToResponseList(bookList);
//    }

    @Override
    public List<BookDto> findAll() {
        return bookMapper.mapEntityListToResponseList(bookRepository.findAll());
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
        return bookMapper.mapEntityListToResponseList(bookRepository.findByCategory(category));
    }


}
