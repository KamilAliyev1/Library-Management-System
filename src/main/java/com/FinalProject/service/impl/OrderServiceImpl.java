package com.FinalProject.service.impl;


import com.FinalProject.dto.OrderGETv1;
import com.FinalProject.dto.OrderPOSTv1;
import com.FinalProject.exception.NotChangeableException;
import com.FinalProject.exception.NotFoundException;
import com.FinalProject.mapper.OrderMapper;
import com.FinalProject.model.Books;
import com.FinalProject.model.Order;
import com.FinalProject.repository.OrderRepo;
import com.FinalProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService<OrderGETv1, OrderPOSTv1, OrderPOSTv1> {

    private final OrderRepo orderRepo;

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;


    // TODO: 4/11/2023 ADD Book service


    @Transactional
    @Override
    public OrderGETv1 add(OrderPOSTv1 dto) {

        Order order = orderMapper.toEntity(dto);

        // TODO: 4/13/2023 check available
        
        order = orderRepo.saveAndFlush(order);

        // TODO: 4/11/2023 -1 from book stock

        return orderMapper.toGetDto(order);
    }

    @Override
    public OrderGETv1 get(Long ID) {

        var order = orderRepo.findById(ID);

        if(order.isEmpty()) throw new NotFoundException("not founded");

        return orderMapper.toGetDto(order.get());
    }

    @Override
    public OrderGETv1 update(Long id,OrderPOSTv1 dto) {

        var optional = orderRepo.findById(id);

        if(optional.isEmpty()) throw new NotFoundException();

        Order order = optional.get();

        if(order.getInProgress()==false)throw new NotChangeableException("Cannot be changeable");

        if(!order.getCreatedAt().toLocalDate().equals(LocalDate.now()))throw new NotChangeableException("create new order");

        // TODO: 4/15/2023 check available
        var dtoBooks= dto.getBooks().stream().filter(t->t!=null).map(t-> Books.builder().id(t).build()).collect(Collectors.toSet());


        var newBooks = dtoBooks.stream().filter(t->!order.getBooks().contains(t)).collect(Collectors.toList());
        // TODO: 4/15/2023 -1 stock newBooks

        var returnedBooks = order.getBooks().stream().filter(t->!dtoBooks.contains(t)).collect(Collectors.toList());
        // TODO: 4/15/2023 +1 stock


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
        orderRepo.disableProgress(ID,LocalDateTime.now());
        // TODO: 4/11/2023 +1 to book stock
    }

    @Override
    public List<OrderGETv1> getAll() {
        return orderRepo.findAll().stream().map(orderMapper::toGetDto).collect(Collectors.toList());
    }


}
