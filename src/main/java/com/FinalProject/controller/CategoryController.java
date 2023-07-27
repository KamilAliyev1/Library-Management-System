package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.CategoryDto;
import com.FinalProject.service.CategoryService;
import com.FinalProject.service.impl.BookServiceImpl;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final BookServiceImpl bookService;


    public CategoryController(CategoryService categoryService, BookServiceImpl bookService) {
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @GetMapping("/categories")
    public String findAll(Model model) {
        final List<CategoryDto> categoryList = categoryService.findAllCategories();
        model.addAttribute("categories", categoryList);
        return "categories-list";
    }

    @GetMapping("/category/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        final CategoryDto category = categoryService.findCategoryById(id);

        model.addAttribute("category", category);
        return "category-list";
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


    @GetMapping("/update/{id}")
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


    @GetMapping("/remove/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        categoryService.deleteCategory(id);
        model.addAttribute("category", categoryService.findAllCategories());
        return "redirect:/categories";
    }


    @GetMapping("/search/categoryName")
    public String showBooksByCategoryName(@RequestParam String category, Model model) {
        List<BookDto> bookList = bookService.findAll();
        List<BookDto> findBooks =
                bookList.stream().
                        filter(bookDto -> bookDto.getCategory().contains(category))
                        .collect(Collectors.toList());
        model.addAttribute("bookList", findBooks);
        return "categories-list";
    }
}
