package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.CategoryDto;
import com.FinalProject.service.BookService;
import com.FinalProject.service.CategoryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final BookService bookService;


    public CategoryController(CategoryService categoryService, BookService bookService) {
        this.categoryService = categoryService;
        this.bookService = bookService;

    }

    @GetMapping("/categories")
    public String findAll(Model model) {
        final List<CategoryDto> categoryList = categoryService.findAllCategories();
        model.addAttribute("categories", categoryList);
//        return "categories-list";
        return "category-list";
    }

    @GetMapping("/search/category")
    public String findByName(@RequestParam String name, Model model) {
        CategoryDto categoryDto = categoryService.findCategoryByName(name);
        model.addAttribute("categories", categoryDto);
        return "category-list";

    }

    @GetMapping("/search/books")
    public String showBooksByCategoryName(@RequestParam String book, Model model) {
        List<BookDto> bookList = bookService.findAll();
        List<BookDto> bookDtoList = new ArrayList<BookDto>();
        for (BookDto b : bookList) {
            if (b.getCategory().equals(book))
                bookDtoList.add(b);
        }

//        List<BookDto> findBooks =
//                bookList.stream().
//                        filter(bookDto -> bookDto.getCategory().contains(book))
//                        .collect(Collectors.toList());
        model.addAttribute("bookList", bookDtoList);
        return "categoryBook-list";
    }

    @GetMapping("/categories/new")
    public String categoryForm(Model model) {
        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("category", categoryDto);
        return "category-create";
    }

    @PostMapping("/categories")
    public String createCategory(@ModelAttribute("category") CategoryDto category) {
        categoryService.createCategory(category);
        return "redirect:/categories";
    }


    @GetMapping("/category/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        final CategoryDto category = categoryService.findCategoryById(id);
        model.addAttribute("category", category);
        return "category-update";
    }


    @PostMapping("/update-category/{id}")
    public String updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto category) {
        categoryService.updateCategory(id, category);
        return "redirect:/categories";
    }


    @GetMapping("/category/remove/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        categoryService.deleteCategory(id);
        model.addAttribute("category", categoryService.findAllCategories());
        return "redirect:/categories";
    }

}
