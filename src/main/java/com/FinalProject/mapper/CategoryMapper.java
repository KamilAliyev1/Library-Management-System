package com.FinalProject.mapper;

import com.FinalProject.dto.CategoryDto;
import com.FinalProject.model.Category;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto categoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .books(category.getBooks())
                .build();
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .books(categoryDto.getBooks())
                .build();
    }
}

