package com.FinalProject.service.impl;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.BookRequest;
import com.FinalProject.exception.BookAlreadyFoundException;
import com.FinalProject.exception.BookNotFoundException;
import com.FinalProject.exception.FileAlreadyExistsException;
import com.FinalProject.model.Authors;
import com.FinalProject.model.Book;
import com.FinalProject.model.Category;
import com.FinalProject.repository.BookRepository;
import com.FinalProject.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BookServiceImpl {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final Path root = Paths.get("/Users/huseyn007/Desktop/Library-Management-System/src/main/resources/static/images");

    private static List<BookDto> entityListToResponseList(List<Book> books) {
        return books.stream().map(book -> new BookDto(
                book.getId(), book.getName(), book.getIsbn(), book.getStock(), book.getAuthor().getFullName(), book.getCategory().getName(), book.getImage())).toList();
    }

    private static BookDto entityToResponse(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .stock(book.getStock())
                .authorName(book.getAuthor().getFullName())
                .category(book.getCategory().getName())
                .isbn(book.getIsbn())
                .name(book.getName())
                .image(book.getImage())
                .build();
    }

    private static List<Book> requestListToEntityList(List<BookRequest> bookRequests) {

        return bookRequests.stream().map(bookRequest ->
                new Book(bookRequest.getIsbn(), bookRequest.getName(), bookRequest.getStock(),
                        Authors.builder()
                                .fullName(bookRequest.getAuthorName())
                                .build(), Category.builder()
                        .name(bookRequest.getCategory())
                        .build()
                )).toList();

    }

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
        if (isPng(multipartFile))
            try {
                Files.copy(multipartFile.getInputStream(), this.root.resolve((Objects.requireNonNull(multipartFile.getOriginalFilename()))));
            } catch (IOException e) {
                throw new FileAlreadyExistsException("file is already found :" + multipartFile.getOriginalFilename());
            }
        else {
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
        return Objects.equals(file.getContentType(), "image/png");
    }


    @Transactional
    public void create(BookRequest bookRequests) {

        var book = requestToEntity(bookRequests);
        Category category = Category.builder()
                .name(bookRequests.getCategory())
                .build();
        book.setCategory(category);
        Optional<Book> bookOptional = bookRepository.findByIsbn(book.getIsbn());
        if (bookOptional.isPresent())
            throw new BookAlreadyFoundException("Book already found with isbn : " + bookOptional.get().getIsbn());
        Optional<Category> categoryOptional = categoryRepository.findByName(bookRequests.getCategory());
        if (categoryOptional.isPresent())
            book.setCategory(categoryOptional.get());
        else
            categoryRepository.save(category);
        save(bookRequests.getFile());
        bookRepository.save(book);

    }


    public Book requestToEntity(BookRequest bookRequest) {
        return Book.builder()
                .isbn(bookRequest.getIsbn())
                .name(bookRequest.getName())
                .stock(bookRequest.getStock())
                .author(Authors.builder().
                        fullName(bookRequest.getAuthorName())
                        .build())
                .image(bookRequest.getFile().getOriginalFilename())
                .build();
    }

    @Transactional
    public void update(String isbn, BookRequest bookRequest) {
        Category bookCategory = Category.builder()
                .name(bookRequest.getCategory())
                .build();
        Authors author = Authors.builder()
                .fullName(bookRequest.getAuthorName())
                .build();
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book not found with isbn : " + isbn));
        Optional<Category> category = categoryRepository.findByName(bookCategory.getName());
        if (category.isPresent())
            book.setCategory(category.get());
        else {
            categoryRepository.save(bookCategory);
            book.setCategory(bookCategory);
        }
        book.setAuthor(author);
        book.setStock(bookRequest.getStock());
        book.setIsbn(bookRequest.getIsbn());
        book.setName(bookRequest.getName());
        book.setImage(bookRequest.getFile().getOriginalFilename());
        save(bookRequest.getFile());
        bookRepository.save(book);
    }

    @Transactional
    public void delete(String isbn) {
        bookRepository.findByIsbn(isbn).ifPresentOrElse(
                book -> {
                    bookRepository.deleteByIsbn(isbn);
                    deleteFile(book.getImage());
                },
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

    public void updateStockNumbersByIdIn(List<Long> books, int i) {
        bookRepository.updateStockNumbersByIdIn(books, i);
    }


//    public void updateStockNumbersByIdIn(List<Long> ids, int amount) {
//        bookRepository.(ids, amount);
//    }


}
