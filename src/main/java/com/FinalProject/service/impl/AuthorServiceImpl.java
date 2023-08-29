package com.FinalProject.service.impl;

import com.FinalProject.dto.AuthorsDto;
import com.FinalProject.dto.BookDto;
import com.FinalProject.request.BookRequest;
import com.FinalProject.exception.AuthorsNotFoundException;
import com.FinalProject.mapper.AuthorsMapper;
import com.FinalProject.model.Authors;
import com.FinalProject.model.Book;
import com.FinalProject.repository.AuthorRepository;
import com.FinalProject.repository.BookRepository;
import com.FinalProject.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorsMapper authorsMapper;
    private final BookRepository bookRepository;

    @Override
    public List<AuthorsDto> getAuthors() {
        List<Authors> authorsList = authorRepository.findAllByDeleteFalseOrderByIdDesc();
        List<AuthorsDto> authorsDtoList = new ArrayList<>();
        for (Authors authors : authorsList) {
            authorsDtoList.add(authorsMapper.fromAuthorModelToDto(authors));
        }
        return authorsDtoList;
    }

    @Override
    public void createAuthor(AuthorsDto authorsDto) {
        var author = authorsMapper.fromAuthorDtoToModel(authorsDto);
        author.setDelete(false);
        author.setBirthDate(authorsDto.getBirthDate());
        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Long id, AuthorsDto request) {
        Authors author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorsNotFoundException("Author couldn't found with %s id:" + id));
        author.setFullName(request.getFullName());
        author.setEmail(request.getEmail());
        author.setPhone(request.getPhone());
        author.setAddress(request.getAddress());
        author.setBirthDate(request.getBirthDate());
        authorRepository.save(author);
    }

    @Override
    public AuthorsDto viewAuthor(Long id) {
        Authors author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorsNotFoundException("Author couldn't found with %s id:" + id));
        return authorsMapper.fromAuthorModelToDto(author);
    }

    @Override
    public List<AuthorsDto> searchBook(String query) {
        List<Authors> authorsList = authorRepository.searchAuthors(query);
        List<AuthorsDto> authorsDtoList = new ArrayList<>();
        for (Authors authors : authorsList) {
            authorsDtoList.add(authorsMapper.fromAuthorModelToDto(authors));
        }
        return authorsDtoList;
    }

    @Override
    public List<BookDto> showAuthorBooks(Long authorId) {
        List<Book> fromDb = bookRepository.findBooksByAuthorId(authorId);
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Book books : fromDb) {
            bookDtoList.add(fromDbToModel(books));
        }
        return bookDtoList;
    }

    @Override
    public void setBookToAuthor(BookRequest bookRequests, Book book) {
        Optional<Authors> authors = authorRepository.findById(bookRequests.getAuthorId());
        authors.ifPresent(book::setAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        Authors author = authorRepository.findById(id).orElseThrow();
        author.setDelete(true);
        authorRepository.save(author);
    }

    private BookDto fromDbToModel(Book books) {
        return BookDto.builder()
                .id(books.getId())
                .name(books.getName())
                .category(books.getCategory().getName())
                .build();
    }
}
