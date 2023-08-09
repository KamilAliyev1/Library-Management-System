package com.FinalProject.service.impl;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.BookRequest;
import com.FinalProject.exception.*;
import com.FinalProject.mapper.BookMapper;
import com.FinalProject.model.Authors;
import com.FinalProject.model.Book;
import com.FinalProject.repository.AuthorRepository;
import com.FinalProject.repository.BookRepository;
import com.FinalProject.repository.CategoryRepository;
import com.FinalProject.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    private final Path root = Paths.get("src/main/resources/static/images");

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void save(MultipartFile multipartFile) {
        if (isPng(multipartFile)) {
            try {
                Files.copy(
                        multipartFile.getInputStream(),
                        this.root.resolve(
                                (Objects.requireNonNull(multipartFile.getOriginalFilename())
                                )
                        )
                );
            } catch (IOException e) {
                throw new FileAlreadyExistsException("file is already found :" + multipartFile.getOriginalFilename());
            }
        } else {
            throw new IllegalArgumentException("Only PNG files are allowed");
        }

    }

    public void deleteFile(String fileName) {
        Path path = Paths.get(root.toString(), fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPng(MultipartFile file) {
        return file.getContentType().equals("image/png");
    }


    @Transactional
    public void create(BookRequest bookRequests) {
        var book = requestToEntity(bookRequests);
        var category = categoryRepository.findById(bookRequests.getCategoryId()).orElseThrow(() ->
                new CategoryNotFoundException("Not found Category such id: " + bookRequests.getCategoryId())
        );
        var author = authorRepository.findById(bookRequests.getAuthorId()).orElseThrow(() ->
                new AuthorsNotFoundException("Not found Author such id = " + bookRequests.getAuthorId())
        );

        book.setCategory(category);
        book.setAuthor(author);
        Optional<Book> bookOptional = bookRepository.findByIsbn(book.getIsbn());

        if (bookOptional.isPresent())
            throw new BookAlreadyFoundException("Book already found with isbn: " + bookOptional.get().getIsbn());

        save(bookRequests.getFile());
        bookRepository.save(book);
    }

    public Book requestToEntity(BookRequest bookRequest) {
        return Book.builder().isbn(bookRequest.getIsbn()).name(bookRequest.getName()).stock(bookRequest.getStock())
//                .author(Authors.builder().
//                        fullName(bookRequest.getAuthorName())
//                        .build())
                .image(bookRequest.getFile().getOriginalFilename()).build();
    }

    @Override
    public List<BookDto> create(List<BookRequest> bookRequests) {
        return null;
    }

    public BookDto update(String isbn, BookRequest bookRequest) {
        Book book = bookRepository
                .findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with isbn : " + isbn));


        var category = categoryRepository.findById(bookRequest.getCategoryId()).orElseThrow(() ->
                new CategoryNotFoundException("Not found Category such id: " + bookRequest.getCategoryId())
        );
        var author = authorRepository.findById(bookRequest.getAuthorId()).orElseThrow(() ->
                new AuthorsNotFoundException("Not found Author such id = " + bookRequest.getAuthorId())
        );

        book.setCategory(category);
        book.setAuthor(author);
        book.setStock(bookRequest.getStock());
        book.setIsbn(bookRequest.getIsbn());
        book.setName(bookRequest.getName());
        book.setImage(bookRequest.getFile().getOriginalFilename());
        save(bookRequest.getFile());
        return bookMapper.mapEntityToResponse(bookRepository.save(book));
    }

    @Transactional
    public void delete(String isbn) {
        bookRepository.findByIsbn(isbn).ifPresentOrElse(book -> {
            bookRepository.deleteByIsbn(isbn);
            deleteFile(book.getImage());
        }, () -> {
            throw new BookNotFoundException("Book not found with isbn : " + isbn);
        });

    }

    public List<BookDto> findByCategory(String category) {
        if (bookRepository.findByCategory(category).isEmpty())
            throw new BookNotFoundException("Book not found with category :  " + category);
        return bookMapper.mapEntityListToResponseList(bookRepository.findByCategory(category));
    }

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

    public List<BookDto> findByAuthor(String authorFullName) {
        Authors author = new Authors();
        author.setFullName(authorFullName);
        List<Book> bookList = bookRepository.findByAuthor(author);
        if (bookList.isEmpty()) throw new BookNotFoundException("Book not found with author : " + author.getFullName());

        return bookMapper.mapEntityListToResponseList(bookList);
    }

    public List<BookDto> findAll() {
        return bookMapper.mapEntityListToResponseList(bookRepository.findAll());
    }

    public boolean areAllBooksInStock(List<Long> id) {
        return bookRepository.areAllBooksInStock(id);
    }

    public void updateStockNumbersByIdIn(List<Long> books, int i) {
        bookRepository.updateStockNumbersByIdIn(books, i);
    }

}
