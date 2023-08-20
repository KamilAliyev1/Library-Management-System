package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.CategoryDto;
import com.FinalProject.service.BookService;
import com.FinalProject.service.CategoryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final BookService bookService;

    @GetMapping("/categories")
    public String findAll(Model model) {
        List<CategoryDto> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return "category-list";
    }

    @GetMapping("/search/category")
    public String findByName(@RequestParam String name, Model model) {
        List<CategoryDto> categories = categoryService.findCategoryByName(name);
        model.addAttribute("categories", categories);

        return "category-list";
    }

    @GetMapping("/search/books")
    public String findByCategory(Long categoryId, Model model) {
        List<BookDto> books = bookService.searchBooks(null, categoryId, null);
        model.addAttribute("books", books);

        return "category-book-list";
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
