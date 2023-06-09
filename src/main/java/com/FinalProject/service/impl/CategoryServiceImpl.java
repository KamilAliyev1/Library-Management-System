package com.FinalProject.service.impl;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.CategoryDto;
import com.FinalProject.exception.CategoryAlreadyExistsException;
import com.FinalProject.exception.CategoryNotFoundException;
import com.FinalProject.mapper.CategoryMapper;
import com.FinalProject.model.Category;
import com.FinalProject.repository.CategoryRepository;
import com.FinalProject.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final BookServiceImpl bookService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, BookServiceImpl bookService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.bookService = bookService;
    }

    @Override
    public List<CategoryDto> findAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        Category optionalCategory = categoryRepository.findById(id).get();
        CategoryDto categoryDto = categoryMapper.categoryToCategoryDto(optionalCategory);
//        List<BookDto> bookRequest = bookService.findByCategory(categoryDto.getName());
        if (optionalCategory == null) {
            throw new CategoryNotFoundException("No  category present with id=" + id);
        } else {
            return categoryDto;
        }
    }


    @Override
    public void createCategory(CategoryDto category) {
        Category category1 = categoryMapper.categoryDtoToCategory(category);
        if (category1 != null) {
            categoryRepository.save(new Category(category1.getId(), category1.getName(), category1.getBook()));
        } else {
            throw new CategoryAlreadyExistsException("Category already exists!");

        }
    }


    @Override
    public void updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).get();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);


    }


    @Override
    public void deleteCategory(Long id) {
        final Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Not found Category such id= " + id));
        categoryRepository.deleteById(category.getId());
    }
}
