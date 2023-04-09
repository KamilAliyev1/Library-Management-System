package com.FinalProject.mapper;

import com.FinalProject.dto.AuthorsDto;
import com.FinalProject.model.Authors;
import org.springframework.stereotype.Component;

@Component
public class AuthorsMapper {
    public AuthorsDto fromAuthorModelToDto(Authors authors) {
        return AuthorsDto.builder()
                .id(authors.getId())
                .fullName(authors.getFullName())
                .phone(authors.getPhone())
                .email(authors.getEmail())
                .address(authors.getAddress())
                .birthDate(authors.getBirthDate())
                .build();
    }

    public Authors fromAuthorDtoToModel(AuthorsDto authorsDto) {
        return Authors.builder()
                .fullName(authorsDto.getFullName())
                .email(authorsDto.getEmail())
                .phone(authorsDto.getPhone())
                .address(authorsDto.getAddress())
                .birthDate(authorsDto.getBirthDate())
                .build();
    }
}
