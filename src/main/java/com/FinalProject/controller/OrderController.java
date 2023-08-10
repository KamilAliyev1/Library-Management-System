package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.OrderRequest;
import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.mapper.OrderMapper;
import com.FinalProject.service.BookService;
import com.FinalProject.service.OrderService;
import com.FinalProject.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final BookService bookService;
    private final OrderService orderService;
    private final StudentService studentService;

    @GetMapping("/add")
    String addPage(Model model) {
        var order = new OrderRequest();
        List<StudentDto> students = studentService.getStudents();
        List<BookDto> books = bookService.findAll();

        model.addAttribute("order", order);
        model.addAttribute("books", books);
        model.addAttribute("students", students);
        return "order-create";
    }

    @PostMapping("/add")
    String addOrder(Model model, @ModelAttribute("order") @Valid OrderRequest orderRequest) {
        orderService.add(orderRequest);
        return "redirect:/orders";
    }

    @GetMapping("/search/")
    String get(@RequestParam Long id, Model model) {
        model.addAttribute("orders", List.of(orderService.get(id)));
        return "order-list";
    }

    @GetMapping("/{id}/disableProgress")
    String disableProgress(@PathVariable Long id) {
        orderService.disableProgress(id);
        return "redirect:/orders";
    }

    @GetMapping
    String getAll(Model model) {
        var orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "order-list";
    }

    @GetMapping("/{id}/delete")
    String delete(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }

    @PostMapping("/{id}/update")
    String update(@PathVariable Long id, @ModelAttribute("order") @Valid OrderRequest dto, Model model) {

        var order = orderService.update(id, dto);
        model.addAttribute("order", order);

        return "order-update";
    }

    @GetMapping("/{id}/update")
    String updatePage(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.get(id));
        return "order-update";
    }
}
