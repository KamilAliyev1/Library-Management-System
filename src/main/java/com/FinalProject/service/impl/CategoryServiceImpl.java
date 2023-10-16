package com.FinalProject.service.impl;

import com.FinalProject.dto.CategoryDto;
import com.FinalProject.exception.BooksExistsWithThisCategoryException;
import com.FinalProject.exception.CategoryAlreadyExistsException;
import com.FinalProject.exception.CategoryNotFoundException;
import com.FinalProject.mapper.CategoryMapper;
import com.FinalProject.model.Book;
import com.FinalProject.model.Category;
import com.FinalProject.repository.BookRepository;
import com.FinalProject.repository.CategoryRepository;
import com.FinalProject.request.BookRequest;
import com.FinalProject.request.CategoryRequest;
import com.FinalProject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    @Override
    public List<CategoryDto> findAllCategories() {

        List<Category> categories = categoryRepository.findAllByOrderByIdDesc();
        return categories.stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findCategoryByName(String name) {
        List<Category> categories = categoryRepository.findCategoriesByNameContainingIgnoreCase(name);
        return categoryMapper.categoryDtoListToCategoryList(categories);
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
//        Category category = categoryRepository.findById(id).get();
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category is not found with id: " + id));
        return categoryMapper.categoryToCategoryDto(category);

    }

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        List<Category> categories = categoryRepository.findCategoriesByNameContainingIgnoreCase(categoryRequest.getName());
        Optional<Category> optional = categories.size() == 0 ?
                Optional.empty() :
                Optional.of(categories.get(0));
        optional.ifPresentOrElse(ctg -> {
            throw new CategoryAlreadyExistsException("Category with name  " + categoryRequest.getName() + " already exists!");
        }, () -> {
            Category category = categoryMapper.categoryRequestToCategory(categoryRequest);
            categoryRepository.save(category);
        });
    }

    @Override
    public void updateCategory(Long id, CategoryDto categoryDto) {
        Category category = fetchCategoryIfExists(id);
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
    }

    public Category fetchCategoryIfExists(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category don't find with id: " + id));
    }

//    public boolean fetchBooksIfExists(Category category) {
//        String name = category.getName();
//        List<Book> books = bookRepository.findByCategory(name);
//        Optional<Book> optional = books.size() == 0 ?
//                Optional.empty() :
//                Optional.of(books.get(0));
//        return optional.isPresent();
//    }

    @Override
    public void deleteCategory(Long id) {
//        Category category = fetchCategoryIfExists(id);
//        String name = category.getName();
//        List<Book> books = bookRepository.findByCategory(name);
//        Optional<Book> optional = books.size() == 0 ?
//                Optional.empty() :
//                Optional.of(books.get(0));
//
//        optional.ifPresentOrElse(opt -> {
//            throw new BooksExistsWithThisCategoryException("Book with name  " + name + "  exists!");
//        }, () -> {
//
//            categoryRepository.deleteById(id);
//        });

        Category category=fetchCategoryIfExists(id);
        category.setDeleteStatus(true);
        categoryRepository.save(category);
    }

    @Override
    public void setBookToCategory(BookRequest bookRequests, Book book) {
        Optional<Category> category = categoryRepository.findById(bookRequests.getCategoryId());
        category.ifPresent(book::setCategory);
    }

}

