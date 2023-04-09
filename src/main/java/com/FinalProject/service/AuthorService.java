package com.FinalProject.service;

import com.FinalProject.dto.AuthorsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
//● List
//● Create
//● Update
//● View
//● Delete
//● Show author books

    List<AuthorsDto> getAuthors();
    void createAuthor(AuthorsDto authorsDto);
    void updateAuthor(Long id , AuthorsDto authorsDto);
    AuthorsDto viewAuthor(Long id);
    void deleteAuthor(Long id);
    List<AuthorsDto> searchBook(String query);
    //showAuthorBooks


}
