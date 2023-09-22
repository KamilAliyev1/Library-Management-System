package com.FinalProject.service.impl;

import com.FinalProject.dto.OrderDto;
import com.FinalProject.dto.OrderRequest;
import com.FinalProject.exception.NotDeletableException;
import com.FinalProject.exception.OrderNotFoundException;
import com.FinalProject.exception.StudentNotFoundException;
import com.FinalProject.mapper.OrderMapper;
import com.FinalProject.model.Book;
import com.FinalProject.model.Order;
import com.FinalProject.repository.OrderRepo;
import com.FinalProject.repository.StudentRepository;
import com.FinalProject.service.OrderService;
import com.FinalProject.service.OrderValidator;
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

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final StudentRepository studentRepository;
    private final OrderMapper orderMapper;
    private final BookServiceImpl bookService;
    private final OrderValidator orderValidator;

    @Transactional
    @Override
    public void add(OrderRequest dto) {

        var student = studentRepository
                .findById(dto.studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student don't find with id: " + dto.studentId));

        if (student == null) throw new StudentNotFoundException();

        Order order = orderMapper.toEntity(dto);

        List<Long> books = new HashSet<>(dto.getBooks()).stream().filter(Objects::nonNull).toList();

        orderValidator.validateBooksConfusionExceptOrder(
                student,
                books
                        .stream()
                        .map(t -> Book
                                .builder()
                                .id(t)
                                .build()
                        ).toList(),
                null
        );

        orderValidator.validateNewOrderPermission(student);

        orderValidator.validateBooks(books);

        order = orderRepo.saveAndFlush(order);

        bookService.updateStockNumbersByIdIn(books, -1);
    }

    @Override
    public OrderDto get(Long ID) {

        var order = orderRepo.findById(ID).orElseThrow(
                () -> new OrderNotFoundException(
                        String.format("Order couldn't found with id: %d", ID)
                )
        );

        return orderMapper.toGetDto(order);
    }

    @Transactional
    @Override
    public OrderDto update(Long id, OrderRequest dto) {

        var student = studentRepository
                .findById(dto.studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student don't find with id: " + dto.studentId));

        if (student == null) throw new StudentNotFoundException();

        Order order = orderRepo.findById(id).orElseThrow(
                () -> new OrderNotFoundException(
                        String.format("Order couldn't found with id: %d", id)
                )
        );

        orderValidator.validateOrderForUpdate(order);

        var dtoBooksIds = dto
                .getBooks()
                .stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        var entityBooksIds = order.getBooks().stream().map(Book::getId).collect(Collectors.toList());

        if (dtoBooksIds.isEmpty()) {
            disableProgress(id);
            delete(id);
            return orderMapper.toGetDto(new Order());
        }

        orderValidator.validateBooks(
                dtoBooksIds
                        .stream()
                        .filter(t -> !entityBooksIds.contains(t))
                        .collect(Collectors.toList()));

        var dtoBooks = dtoBooksIds.stream().map(t -> Book.builder().id(t).build()).collect(Collectors.toSet());

        orderValidator.validateBooksConfusionExceptOrder(student, dtoBooks.stream().toList(), order);

        bookService.updateStockNumbersByIdIn(entityBooksIds, 1);

        bookService.updateStockNumbersByIdIn(dtoBooksIds, -1);

        var newOrder = orderMapper.setDtoChangesToEntity(dto, order);

        newOrder.setBooks(dtoBooks); // I set this in here .Because DTO's Books can be contain null variable
        newOrder.setStudent(student);

        newOrder = orderRepo.saveAndFlush(newOrder);

        return orderMapper.toGetDto(newOrder);
    }

    @Override
    public void delete(Long ID) {

        if (orderRepo.isInProgress(ID)) throw new NotDeletableException("still in progress");

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

        var order = orderRepo.findById(ID).orElseThrow(
                () -> new OrderNotFoundException("order not founded")
        );

        if (!order.getInProgress()) return;

        order.setFinishedAt(LocalDate.now());

        order.setInProgress(false);

        order.setInDelay(false);

        bookService.updateStockNumbersByIdIn(order.getBooks().stream().map(Book::getId).toList(), 1);

        orderRepo.save(order);
    }

    @Override
    public List<OrderDto> getAll() {
        return orderRepo
                .findAllByOrderByIDDesc()
                .stream()
                .map(orderMapper::toGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> searchOrders(Long studentId, Long bookId) {
        return orderMapper
                .mapEntityListToDtoList(
                        orderRepo.findByStudentIDOrBooks_Id(studentId, bookId)
                );
    }
}
