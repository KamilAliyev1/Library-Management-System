package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.OrderDto;
import com.FinalProject.dto.OrderRequest;
import com.FinalProject.dto.studentdto.StudentDto;
import com.FinalProject.service.BookService;
import com.FinalProject.service.OrderService;
import com.FinalProject.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.FinalProject.exception.ExceptionHandler.setExceptionMessage;


@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final BookService bookService;
    private final OrderService orderService;
    private final StudentService studentService;

    @GetMapping("/add")
    String addPage(Model model, HttpServletRequest request) {
        var order = new OrderRequest();
        List<StudentDto> students = studentService.getStudents();
        List<BookDto> books = bookService.findAll();

        model.addAttribute("order", order);
        model.addAttribute("books", books);
        model.addAttribute("students", students);
        setExceptionMessage(model, request);
        return "orders/order-create";
    }

    @PostMapping("/add")
    String addOrder(Model model, @ModelAttribute("order") @Valid OrderRequest orderRequest) {
        orderService.add(orderRequest);
        return "redirect:/orders";
    }

    @GetMapping("/search")
    String searchOrders(
            @RequestParam(name = "studentId", required = false) Long studentId,
            @RequestParam(name = "bookId", required = false) Long bookId,
            Model model) {

        List<BookDto> books = bookService.findAll();
        List<StudentDto> students = studentService.getStudents();
        List<OrderDto> orders = orderService.searchOrders(studentId, bookId);

        model.addAttribute("books", books);
        model.addAttribute("orders", orders);
        model.addAttribute("students", students);
        return "orders/order-list";
    }

    @GetMapping("/{id}/disableProgress")
    String disableProgress(@PathVariable Long id) {
        orderService.disableProgress(id);
        return "redirect:/orders";
    }

    @GetMapping
    String getAll(Model model, HttpServletRequest request) {
        List<BookDto> books = bookService.findAll();
        List<OrderDto> orders = orderService.getAll();
        List<StudentDto> students = studentService.getStudents();

        model.addAttribute("books", books);
        model.addAttribute("orders", orders);
        model.addAttribute("students", students);
        setExceptionMessage(model, request);
        return "orders/order-list";
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

        return "redirect:/orders/"+id+"/update";
    }

    @GetMapping("/{id}/update")
    String updatePage(@PathVariable Long id, Model model, HttpServletRequest request) {
        OrderDto order = orderService.get(id);
        List<StudentDto> students = studentService.getStudents();
        List<BookDto> books = bookService.findAll();

        model.addAttribute("order", order);
        model.addAttribute("books", books);
        model.addAttribute("students", students);
        setExceptionMessage(model, request);
        return "orders/order-update";
    }


}
