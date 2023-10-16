package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.CategoryDto;
import com.FinalProject.request.CategoryRequest;
import com.FinalProject.service.BookService;
import com.FinalProject.service.CategoryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.FinalProject.exception.ExceptionHandler.setExceptionMessage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final BookService bookService;
    private final CategoryService categoryService;

    @GetMapping
    public String findAll(Model model) {
        List<CategoryDto> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return "categories/category-list";
    }

    @GetMapping("/search")
    public String findCategoryByName(@RequestParam String name, Model model, HttpServletRequest request) {
        setExceptionMessage(model, request);
        List<CategoryDto> categories = categoryService.findCategoryByName(name);
        model.addAttribute("categories", categories);
        return "categories/category-list";
    }

    @GetMapping("/search/books")
    public String findBooksByCategory(Long categoryId, Model model) {
//        setExceptionMessage(model, request);
        List<BookDto> books = bookService.searchBooks(null, categoryId, null);
        model.addAttribute("books", books);
        return "categories/category-book-list";
    }

    @GetMapping("/new")
    public String categoryForm(Model model, HttpServletRequest request) {
//        System.out.println("categoryForm");
        setExceptionMessage(model, request);
        var category = new CategoryRequest();
        model.addAttribute("category", category);
        return "categories/category-create";
    }

    @PostMapping
    public String createCategory(@ModelAttribute("category") CategoryRequest category) {
        categoryService.createCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        setExceptionMessage(model, request);
        final CategoryDto category = categoryService.findCategoryById(id);
        model.addAttribute("category", category);
        return "categories/category-update";
    }

    @PostMapping("/{id}")
    public String updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto category) {
        categoryService.updateCategory(id, category);
        return "redirect:/categories";
    }

    @GetMapping("/{id}/remove")
    public String deleteCategory(@PathVariable("id") Long id, Model model) {
//        setExceptionMessage(model, request);
        categoryService.deleteCategory(id);
        model.addAttribute("category", categoryService.findAllCategories());
        return "redirect:/categories";
    }
}
