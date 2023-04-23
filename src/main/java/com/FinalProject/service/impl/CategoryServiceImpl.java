package com.FinalProject.service.impl;

import com.FinalProject.dto.CategoryDto;
import com.FinalProject.exception.CategoryNotFoundException;
import com.FinalProject.mapper.CategoryMapper;
import com.FinalProject.model.Category;
import com.FinalProject.repository.CategoryRepository;
import com.FinalProject.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
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
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        CategoryDto categoryDto = categoryMapper.categoryToCategoryDto(optionalCategory.get());


        if (optionalCategory != null) {
            return categoryDto;
        } else {
            throw new CategoryNotFoundException("No  category present with id=" + id);
        }
    }


    @Override
    public void createCategory(CategoryDto category) {
        Category category1 = categoryMapper.categoryDtoToCategory(category);

        categoryRepository.save(new Category(category1.getId(), category1.getName(), category1.getBooks()));

//        throw new CategoryAlreadyExistsException("Category already exists!");

    }


    @Override
    public void updateCategory(CategoryDto categoryDto) {
        categoryRepository.save(categoryMapper.categoryDtoToCategory(categoryDto));

    }


    @Override
    public void deleteCategory(Long id) {
        final Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Not found Category such id= " + id));
        categoryRepository.deleteById(category.getId());
    }
}
