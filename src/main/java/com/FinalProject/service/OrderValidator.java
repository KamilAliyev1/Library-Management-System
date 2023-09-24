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

    public void validateBooksConfusionExceptOrder(Student student, List<Book> books, Order order) {

        List<Book> previousBooks = student
                .getOrders()
                .stream()
                .filter(t -> !t.equals(order))
                .filter(Order::getInProgress)
                .flatMap(t -> t.getBooks().stream())
                .toList();

        for (var i:books) {
            if(previousBooks.contains(i))throw new HaveAlreadyBookException(String.format("Student have %s book in previous orders",bookService.findById(i.getId()).getName()));
        }
    }

    public void validateNewOrderPermission(Student student) {
        if (student.getOrders().stream().anyMatch(t -> t.getCreatedAt().equals(LocalDate.now())))
            throw new OrderMustUpdateException("Can't add new order in same day. Please update today's order");
    }

    public void validateBooks(List<Long> books) {
        if (!bookService.areAllBooksInStock(books))
            throw new StockNotEnoughException("stock not enough");
    }

    public void validateOrderForUpdate(Order order) {

        if (!order.getInProgress()) throw new NotChangeableException("Cannot be changeable");

        if (!order.getCreatedAt().equals(LocalDate.now())) throw new NotChangeableException("This order locked.Please create new order");
    }
}

