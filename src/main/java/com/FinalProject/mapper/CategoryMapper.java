package com.FinalProject.mapper;

import com.FinalProject.dto.CategoryDto;
import com.FinalProject.model.Category;
import com.FinalProject.request.CategoryRequest;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto categoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .book(categoryDto.getBooks())
                .build();
    }

    public Category categoryRequestToCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }


}

