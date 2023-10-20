package com.FinalProject.service;

import com.FinalProject.dto.AuthorsDto;
import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.BookRequest;
import com.FinalProject.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {


    List<AuthorsDto> getAuthors();

    void createAuthor(AuthorsDto authorsDto);

    void updateAuthor(Long id, AuthorsDto authorsDto);

    AuthorsDto viewAuthor(Long id);

    void deleteAuthor(Long id);

    List<AuthorsDto> searchBook(String query);

    List<BookDto> showAuthorBooks(Long authorId);

    void setBookToAuthor(BookRequest bookRequests, Book book);


}
