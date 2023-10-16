package com.FinalProject.mapper;

import com.FinalProject.dto.BookDto;
import com.FinalProject.model.Book;
import com.FinalProject.request.BookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {

    public BookDto mapEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .category(book.getCategory().getName())
                .build();
    }

    public BookDto mapEntityToResponse(Book book) {
        return BookDto
                .builder()
                .id(book.getId())
                .stock(book.getStock())
                .authorName(book.getAuthor().getFullName())
                .category(book.getCategory().getName())
                .isbn(book.getIsbn()).name(book.getName())
                .image(book.getImage())
                .categoryId(book.getCategory().getId())
                .authorId(book.getAuthor().getId())
                .build();
    }

    public List<BookDto> mapEntityListToResponseList(List<Book> books) {
        return books
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    public Book mapRequestToEntity(BookRequest bookRequest) {
        return Book.builder()
                .isbn(bookRequest.getIsbn())
                .name(bookRequest.getName())
                .stock(bookRequest.getStock())
                .image(bookRequest.getFile().getOriginalFilename())
                .deleteStatus(false)
                .build();
    }
}
