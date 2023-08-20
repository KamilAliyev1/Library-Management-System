package com.FinalProject.service.impl;

import com.FinalProject.request.BookRequest;
import com.FinalProject.dto.CategoryDto;
import com.FinalProject.exception.CategoryAlreadyExistsException;
import com.FinalProject.exception.CategoryNotFoundException;
import com.FinalProject.mapper.CategoryMapper;
import com.FinalProject.model.Book;
import com.FinalProject.model.Category;
import com.FinalProject.repository.CategoryRepository;
import com.FinalProject.service.BookService;
import com.FinalProject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final BookService bookService;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

//    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, @Lazy BookService bookService) {
//        this.categoryRepository = categoryRepository;
//        this.categoryMapper = categoryMapper;
//        this.bookService = bookService;
//    }

    @Override
    public List<CategoryDto> findAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findCategoryByName(String name) {
        var categories = categoryRepository.findCategoriesByNameContainingIgnoreCase(name);
        return categoryMapper.categoryDtoListToCategoryList(categories);
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).get();
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public void createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.categoryDtoToCategory(categoryDto);
        if (category != null) {
            categoryRepository.save(new Category(category.getId(), category.getName(), category.getBook()));
        } else {
            throw new CategoryAlreadyExistsException("Category already exists!");
        }
    }

    @Override
    public void updateCategory(Long id, CategoryDto categoryDto) {
        if (categoryRepository.findById(id).isPresent()) {
            Category existingCategory = categoryRepository.findById(id).get();
            existingCategory.setName(categoryDto.getName());
            categoryRepository.save(existingCategory);
        } else {
            System.out.println("Category is not present");
        }
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Not found Category such id " + id)
        );
        categoryRepository.deleteById(category.getId());
    }

    @Override
    public void setBookToCategory(BookRequest bookRequests, Book book) {
        Optional<Category> category = categoryRepository.findById(bookRequests.getCategoryId());
        category.ifPresent(book::setCategory);
    }
}

