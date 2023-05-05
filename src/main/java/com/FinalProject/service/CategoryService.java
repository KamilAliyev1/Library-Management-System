package com.FinalProject.service;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.BookRequest;
import com.FinalProject.dto.CategoryDto;
import com.FinalProject.model.Category;

import java.util.List;

public interface CategoryService {
    public List<CategoryDto> findAllCategories();

    public CategoryDto findCategoryById(Long id);

    public void createCategory(CategoryDto category);

    public void updateCategory(Long id,CategoryDto category);

    public void deleteCategory(Long id);

}
