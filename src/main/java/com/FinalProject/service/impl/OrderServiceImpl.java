package com.FinalProject.service.impl;


import com.FinalProject.dto.OrderGETv1;
import com.FinalProject.dto.OrderPOSTv1;
import com.FinalProject.exception.NotChangeableException;
import com.FinalProject.exception.OrderNotFoundException;
import com.FinalProject.exception.StockNotEnoughException;
import com.FinalProject.mapper.OrderMapper;
import com.FinalProject.model.Book;
import com.FinalProject.model.Order;
import com.FinalProject.repository.OrderRepo;
import com.FinalProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService<OrderGETv1, OrderPOSTv1, OrderPOSTv1> {

    private final OrderRepo orderRepo;

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    private final BookServiceImpl bookService;


    @Transactional
    @Override
    public OrderGETv1 add(OrderPOSTv1 dto) {

        Order order = orderMapper.toEntity(dto);

        List<Long> books = new HashSet<>(dto.getBooks()).stream().toList();

        if(!bookService.areAllBooksInStock(books))
            throw new StockNotEnoughException();

        System.out.println(order.getBooks().size()+" sssssss");

        order = orderRepo.saveAndFlush(order);

        System.out.println(order.getBooks().size()+" xccxcxcx");

        bookService.updateStockNumbersByIdIn(books,-1);

        return orderMapper.toGetDto(order);
    }

    @Override
    public OrderGETv1 get(Long ID) {

        var order = orderRepo.findById(ID);

        if(order.isEmpty()) throw new OrderNotFoundException("not founded");

        return orderMapper.toGetDto(order.get());

    }

    @Transactional
    @Override
    public OrderGETv1 update(Long id,OrderPOSTv1 dto) {

        var optional = orderRepo.findById(id);

        if(optional.isEmpty()) throw new OrderNotFoundException();

        Order order = optional.get();

        if(!order.getInProgress())throw new NotChangeableException("Cannot be changeable");

        if(!order.getCreatedAt().toLocalDate().equals(LocalDate.now()))throw new NotChangeableException("create new order");

        var bookIds = dto.getBooks().stream().filter(Objects::nonNull).distinct().collect(Collectors.toCollection(ArrayList::new));

        var entityBooksids= order.getBooks().stream().map(Book::getId).collect(Collectors.toList());

        if(bookIds.isEmpty())delete(id);

        else if(!bookService.areAllBooksInStock(bookIds.stream().filter(t->!entityBooksids.contains(t)).collect(Collectors.toList())))
            throw new StockNotEnoughException();

        var dtoBooks= bookIds.stream().map(t-> Book.builder().id(t).build()).collect(Collectors.toSet());

        bookService.updateStockNumbersByIdIn(entityBooksids,1);

        bookService.updateStockNumbersByIdIn(bookIds,-1);

        var newOrder = orderMapper.change(dto,order);

        newOrder.setBooks(dtoBooks);

        newOrder = orderRepo.saveAndFlush(newOrder);

        var getDto = orderMapper.toGetDto(newOrder);

        return  getDto;
    }

    @Override
    public void delete(Long ID) {

        if(orderRepo.isInProgress(ID)) throw new NotChangeableException("still in progress");

        orderRepo.deleteById(ID);
    }

    @Scheduled(cron = "0 0 * * *")
    void checkPeriod(){

        var temp = orderRepo.findAll();
        temp = temp.stream().filter(t->t.getInProgress()).filter(
                t->
                        t.getCreatedAt()
                                .plusDays(15)
                                .compareTo(LocalDateTime.now())<0
        ).map(t->{t.setInDelay(true);return t;}).collect(Collectors.toList());
        orderRepo.saveAll(temp);
    }


    @Transactional
    @Override
    public void disableProgress(Long ID) {

        var optional = orderRepo.findById(ID);

        if(optional.isEmpty())throw new OrderNotFoundException();

        var order = optional.get();

        order.setFinishedAt(LocalDateTime.now());

        order.setInProgress(false);

        order.setInDelay(false);

        bookService.updateStockNumbersByIdIn(order.getBooks().stream().map(Book::getId).toList(),1);

        orderRepo.save(order);

    }

    @Override
    public List<OrderGETv1> getAll() {
        return orderRepo.findAll().stream().map(orderMapper::toGetDto).collect(Collectors.toList());
    }


}
