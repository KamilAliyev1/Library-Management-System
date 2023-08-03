package com.FinalProject.service;

import com.FinalProject.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategories();

    CategoryDto findCategoryById(Long id);

    void createCategory(CategoryDto category);

    void updateCategory(Long id, CategoryDto category);

    void deleteCategory(Long id);

    CategoryDto findByName(String name);

}
