package com.FinalProject.service;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.CategoryDto;
import com.FinalProject.model.Book;
import com.FinalProject.request.BookRequest;

import java.util.List;

public interface CategoryService {
     List<CategoryDto> findAllCategories();

     CategoryDto findCategoryById(Long id);
     CategoryDto findCategoryByName(String name);

     void createCategory(CategoryDto category);

     void updateCategory(Long id,CategoryDto category);

     void deleteCategory(Long id);
     public void setBookToCategory(BookRequest bookRequests, Book book);

//     List<BookDto> showBooksByCategoryName(String name);

}
