package com.FinalProject.service.impl;

import com.FinalProject.dto.AuthorsDto;
import com.FinalProject.mapper.AuthorsMapper;
import com.FinalProject.model.Authors;
import com.FinalProject.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorsMapper authorsMapper;

    @InjectMocks
    private AuthorServiceImpl authorsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenWhenGetAuthorsThanReturnAuthorsDtoList() {
        List<Authors> authorsList = new ArrayList<>();
        List<AuthorsDto> authorsDtoList = new ArrayList<>();

        Authors authors1 = new Authors();
        authors1.setId(1L);
        authors1.setFullName("test 1");

        Authors authors2 = new Authors();
        authors2.setId(2L);
        authors2.setFullName("test 2");

        AuthorsDto authorsDto1 = new AuthorsDto();
        authorsDto1.setId(1L);
        authorsDto1.setFullName("test 1");

        AuthorsDto authorsDto2 = new AuthorsDto();
        authorsDto2.setId(2L);
        authorsDto2.setFullName("test 2");

        authorsList.add(authors1);
        authorsList.add(authors2);
        authorsDtoList.add(authorsDto1);
        authorsDtoList.add(authorsDto2);

        Mockito.when(authorRepository.findByDeleteFalse()).thenReturn(authorsList);
        Mockito.when(authorsMapper.fromAuthorModelToDto(authors1)).thenReturn(authorsDto1);
        Mockito.when(authorsMapper.fromAuthorModelToDto(authors2)).thenReturn(authorsDto2);

        List<AuthorsDto> result = authorsService.getAuthors();

        assertEquals(result, authorsDtoList);
        assertEquals(result.size(), authorsDtoList.size());
        assertEquals(result.get(0).getId(), authorsDtoList.get(0).getId());
        assertEquals(result.get(0).getFullName(), authorsDtoList.get(0).getFullName());

        Mockito.verify(authorRepository, Mockito.times(1)).findByDeleteFalse();

        Mockito.verify(authorsMapper, Mockito.times(1)).fromAuthorModelToDto(authors1);
        Mockito.verify(authorsMapper, Mockito.times(1)).fromAuthorModelToDto(authors2);

    }

    @Test
    public void givenWhenCreateAuthorThanSaveAuthorToDb() {
        AuthorsDto authorsDto = new AuthorsDto();
        Authors authors = new Authors();
        authorsDto.setId(1L);
        authorsDto.setEmail("test@gmail.com");
        authorsDto.setPhone("123");
        authorsDto.setAddress("test");
        authorsDto.setFullName("test");
        authorsDto.setBirthDate(LocalDate.parse("0101-01-01"));

        authors.setId(1L);
        authors.setEmail("test@gmail.com");
        authors.setPhone("123");
        authors.setAddress("test");
        authors.setFullName("test");
        authors.setBirthDate(LocalDate.parse("0101-01-01"));
        authors.setDelete(false);

        Mockito.when(authorsMapper.fromAuthorDtoToModel(authorsDto)).thenReturn(authors);

        authorsService.createAuthor(authorsDto);

        Mockito.verify(authorRepository, Mockito.times(1)).save(authors);

        assertNotEquals(true, (boolean) authors.getDelete());

    }

    @Test
    public void givenWhenUpdateAuthorThanUpdateAuthor() {
        Long authorId = 1L;
        AuthorsDto authors = new AuthorsDto();
        authors.setFullName("test");
        authors.setBirthDate(LocalDate.parse("0101-01-01"));
        authors.setEmail("test@gmail.com");
        authors.setPhone("123");
        authors.setAddress("baku");

        Authors existingAuthor = new Authors();
        existingAuthor.setId(authorId);
        existingAuthor.setFullName("Original Author");
        existingAuthor.setEmail("original.author@example.com");
        existingAuthor.setPhone("987654321");
        existingAuthor.setAddress("Original Address");
        existingAuthor.setBirthDate(LocalDate.parse("1980-01-01"));

        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        Mockito.when(authorRepository.save(existingAuthor)).thenReturn(existingAuthor);

        authorsService.updateAuthor(authorId, authors);
        assertEquals(authors.getFullName(), existingAuthor.getFullName());
        assertEquals(authors.getEmail(), existingAuthor.getEmail());
        assertEquals(authors.getAddress(), existingAuthor.getAddress());
        assertEquals(authors.getBirthDate(), existingAuthor.getBirthDate());
        assertEquals(authors.getPhone(), existingAuthor.getPhone());


        Mockito.verify(authorRepository, Mockito.times(1)).findById(authorId);
        Mockito.verify(authorRepository, Mockito.times(1)).save(existingAuthor);

    }


}