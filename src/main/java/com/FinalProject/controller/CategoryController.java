package com.FinalProject.controller;

import com.FinalProject.dto.CategoryDto;
import com.FinalProject.repository.CategoryRepository;
import com.FinalProject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

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
    public String updateCategory(@PathVariable("id") Long id, @ModelAttribute("category") String category) {
        CategoryDto categoryDto = categoryService.findCategoryById(id);
        categoryDto.setId(id);
        categoryDto.setName(category);
        categoryService.updateCategory(categoryDto);
        return "redirect:/categories";
    }


    @GetMapping("/remove/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        categoryService.deleteCategory(id);
        model.addAttribute("category", categoryService.findAllCategories());
        return "redirect:/categories";
    }
}
