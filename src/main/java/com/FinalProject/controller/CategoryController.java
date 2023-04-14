package com.FinalProject.controller;

import com.FinalProject.dto.CategoryDto;
import com.FinalProject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/all")
    public String findAll(Model model) {
        final List<CategoryDto> categoryList = categoryService.findAllCategories();
        model.addAttribute("categories", categoryList);
        return "categories-list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        final CategoryDto category = categoryService.findCategoryById(id);
        model.addAttribute("category", category);
        return "category-list";
    }

    @GetMapping("/add")
    public String categoryForm(CategoryDto category) {
        return "category-create";
    }

    @PostMapping("/save")
    public String createCategory(@ModelAttribute("category") CategoryDto category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "category-create";
        }
        categoryService.createCategory(category);
        model.addAttribute("category", categoryService.findAllCategories());
        return "redirect:/all";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        final CategoryDto category = categoryService.findCategoryById(id);

        model.addAttribute("category", category);
        return "category-update";
    }


    @RequestMapping("/update-category/{id}")
    public String updateCategory(@PathVariable("id") Long id, CategoryDto category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            category.setId(id);
            return "category-update";
        }

        categoryService.updateCategory(category);
        model.addAttribute("category", categoryService.findAllCategories());
        return "redirect:/categories";
    }


    @GetMapping("/remove/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        categoryService.deleteCategory(id);
        model.addAttribute("category", categoryService.findAllCategories());
        return "redirect:/all";
    }
}
