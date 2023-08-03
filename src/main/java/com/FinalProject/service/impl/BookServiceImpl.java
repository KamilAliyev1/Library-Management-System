package com.FinalProject.service.impl;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.BookRequest;
import com.FinalProject.dto.CategoryDto;
import com.FinalProject.exception.BookNotFoundException;
import com.FinalProject.mapper.BookMapper;
import com.FinalProject.mapper.CategoryMapper;
import com.FinalProject.model.Book;
import com.FinalProject.repository.BookRepository;
import com.FinalProject.service.BookService;
import com.FinalProject.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Lazy
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final FIleService fileService;
    private final BookMapper bookMapper;

    @Transactional
    @Override
    public void create(BookRequest bookRequests) {
        var book = bookMapper.requestToEntity(bookRequests);
        checkBookByIsbn(book);
        CategoryDto categoryDto = categoryService.findCategoryById(bookRequests.getCategoryId());
        Set<Book> books = new HashSet<>();
        books.add(book);
        categoryDto.setBooks(books);
        book.setCategory(categoryMapper.categoryDtoToCategory(categoryDto));
        fileService.save(bookRequests.getFile());
        bookRepository.save(book);
    }


    private void checkBookByIsbn(Book book) {
        bookRepository.findByIsbn(book.getIsbn()).ifPresent(book1 -> {
            throw new RuntimeException("Book not found with isbn:" + book1.getIsbn());
        });
    }


    //    public BookDto update(String isbn, BookRequest bookRequest) {
//        Category bookCategory = Category.builder()
//                .name(bookRequest.getCategory())
//                .build();
//        Authors author = Authors.builder()
//                .fullName(bookRequest.getAuthorName())
//                .build();
//        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book not found with isbn : " + isbn));
//        Optional<Category> category = categoryRepository.findByName(bookCategory.getName());
//        if (category.isPresent()) {
//            book.setCategory(category.get());
//        } else {
//            categoryRepository.save(bookCategory);
//            book.setCategory(bookCategory);
//        }
//        book.setAuthor(author);
//        book.setStock(bookRequest.getStock());
//        book.setIsbn(bookRequest.getIsbn());
//        book.setName(bookRequest.getName());
//        book.setImage(bookRequest.getFile().getOriginalFilename());
//        save(bookRequest.getFile());
//        return entityToResponse(bookRepository.save(book));
//    }
    @Override
    public void delete(String isbn) {
        bookRepository.findByIsbn(isbn).ifPresentOrElse(book -> {
            bookRepository.deleteByIsbn(isbn);
            fileService.deleteFile(book.getImage());
        }, () -> {
            throw new BookNotFoundException("Book not found with isbn : " + isbn);
        });
    }

    @Override
    public List<BookDto> findAll() {
        return bookMapper.entityListToResponseList(bookRepository.findAll());
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
        return bookMapper.entityListToResponseList(bookRepository.findByCategory(category));
    }


}
