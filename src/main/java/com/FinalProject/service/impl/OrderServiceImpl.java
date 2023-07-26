package com.FinalProject.service.impl;


import com.FinalProject.dto.OrderGETv1;
import com.FinalProject.dto.OrderPOSTv1;
import com.FinalProject.exception.*;
import com.FinalProject.mapper.OrderMapper;
import com.FinalProject.model.Book;
import com.FinalProject.model.Order;
import com.FinalProject.model.Student;
import com.FinalProject.repository.OrderRepo;
import com.FinalProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService<OrderGETv1, OrderPOSTv1, OrderPOSTv1> {

    private final OrderRepo orderRepo;

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    private final BookServiceImpl bookService;

    private final StudentServiceImpl studentService;


    void validatePreviousBooksExceptOrder(Student student,List<Book> books,Order order){

        List<Book> previousBooks = student.getOrders().stream().filter(t->!t.equals(order)).flatMap(t->t.getBooks().stream()).toList();

        if(books.stream().anyMatch(previousBooks::contains))throw new HaveAlreadyBookException("student have this book in previous orders");

    }

    void validateStudents(Student student) {

        if(
                student.getOrders().stream().anyMatch(
                        t->t.getCreatedAt().equals(LocalDate.now())
                )
        )throw new OrderMustUpdateException("cant add new order in same day.please update today's order");
    }

    void validateBooks(List<Long> books) {
        if (!bookService.areAllBooksInStock(books))
            throw new StockNotEnoughException();
    }

    void validateOrderForUpdate(Order order) {

        if (!order.getInProgress()) throw new NotChangeableException("Cannot be changeable");

        if (!order.getCreatedAt().equals(LocalDate.now())) throw new NotChangeableException("create new order");
    }

    @Transactional
    @Override
    public OrderGETv1 add(OrderPOSTv1 dto) {

        var student  = studentService.findById(dto.studentId);

        Order order = orderMapper.toEntity(dto);

        List<Long> books = new HashSet<>(dto.getBooks()).stream().filter(Objects::nonNull).toList();

        validatePreviousBooksExceptOrder(student,books.stream().map(t->Book.builder().id(t).build()).toList(),null);

        validateStudents(student);

        validateBooks(books);

        order = orderRepo.saveAndFlush(order);

        bookService.updateStockNumbersByIdIn(books, -1);

        return orderMapper.toGetDto(order);
    }

    @Override
    public OrderGETv1 get(Long ID) {

        var order = orderRepo.findById(ID).orElseThrow(
                () -> new OrderNotFoundException(
                        String.format("Order couldn't found with id: %d", ID)
                )
        );

        return orderMapper.toGetDto(order);

    }

    @Transactional
    @Override
    public OrderGETv1 update(Long id, OrderPOSTv1 dto) {

        var student  = studentService.findById(dto.studentId);

        Order order = orderRepo.findById(id).orElseThrow(
                () -> new OrderNotFoundException(
                        String.format("Order couldn't found with id: %d", id)
                )
        );


        validateOrderForUpdate(order);


        var dtoBooksIds = dto.getBooks().stream().filter(Objects::nonNull).distinct().collect(Collectors.toCollection(ArrayList::new));

        var entityBooksIds = order.getBooks().stream().map(Book::getId).collect(Collectors.toList());

        if (dtoBooksIds.isEmpty()) {
            disableProgress(id);
            delete(id);
            return orderMapper.toGetDto(new Order());
        }

        validateBooks(dtoBooksIds.stream().filter(t -> !entityBooksIds.contains(t)).collect(Collectors.toList()));

        var dtoBooks = dtoBooksIds.stream().map(t -> Book.builder().id(t).build()).collect(Collectors.toSet());

        validatePreviousBooksExceptOrder(student,dtoBooks.stream().toList(),order);

        bookService.updateStockNumbersByIdIn(entityBooksIds, 1);

        bookService.updateStockNumbersByIdIn(dtoBooksIds, -1);

        var newOrder = orderMapper.change(dto, order);

        newOrder.setBooks(dtoBooks); // I set this in here .Because DTO's Books can be contain null variable

        newOrder = orderRepo.saveAndFlush(newOrder);

        var getDto = orderMapper.toGetDto(newOrder);

        return getDto;
    }

    @Override
    public void delete(Long ID) {

        if (orderRepo.isInProgress(ID)) throw new NotChangeableException("still in progress");

        orderRepo.deleteById(ID);
    }

    @Scheduled(cron = "0 0 * * *")
    void checkPeriod() {

        var temp = orderRepo.findAll();
        temp = temp.stream().filter(Order::getInProgress).filter(
                t ->
                        t.getCreatedAt()
                                .plusDays(15).isBefore(LocalDate.now())
        ).peek(t -> t.setInDelay(true)).collect(Collectors.toList());
        orderRepo.saveAll(temp);
    }


    @Transactional
    @Override
    public void disableProgress(Long ID) {

        var optional = orderRepo.findById(ID);

        if (optional.isEmpty()) throw new OrderNotFoundException();

        var order = optional.get();

        order.setFinishedAt(LocalDate.now());

        order.setInProgress(false);

        order.setInDelay(false);

        bookService.updateStockNumbersByIdIn(order.getBooks().stream().map(Book::getId).toList(), 1);

        orderRepo.save(order);

    }

    @Override
    public List<OrderGETv1> getAll() {
        return orderRepo.findAll().stream().map(orderMapper::toGetDto).collect(Collectors.toList());
    }


}
