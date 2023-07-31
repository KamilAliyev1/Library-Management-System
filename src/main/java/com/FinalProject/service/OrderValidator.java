package com.FinalProject.service;

import com.FinalProject.exception.HaveAlreadyBookException;
import com.FinalProject.exception.NotChangeableException;
import com.FinalProject.exception.OrderMustUpdateException;
import com.FinalProject.exception.StockNotEnoughException;
import com.FinalProject.model.Book;
import com.FinalProject.model.Order;
import com.FinalProject.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final BookService bookService;

    public void validateBooksConfusionExceptOrder(Student student, List<Book> books, Order order){

        List<Book> previousBooks = student.getOrders().stream().filter(t->!t.equals(order)).flatMap(t->t.getBooks().stream()).toList();

        if(books.stream().anyMatch(previousBooks::contains))throw new HaveAlreadyBookException("student have this book in previous orders");

    }

    public void validateNewOrderPermission(Student student) {

        if(
                student.getOrders().stream().anyMatch(
                        t->t.getCreatedAt().equals(LocalDate.now())
                )
        )throw new OrderMustUpdateException("can't add new order in same day.Please update today's order");
    }

    public void validateBooks(List<Long> books) {
        if (!bookService.areAllBooksInStock(books))
            throw new StockNotEnoughException();
    }

    public void validateOrderForUpdate(Order order) {

        if (!order.getInProgress()) throw new NotChangeableException("Cannot be changeable");

        if (!order.getCreatedAt().equals(LocalDate.now())) throw new NotChangeableException("create new order");
    }

}

