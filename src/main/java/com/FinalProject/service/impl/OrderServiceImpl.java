package com.FinalProject.service.impl;


import com.FinalProject.dto.OrderGETv1;
import com.FinalProject.dto.OrderPOSTv1;
import com.FinalProject.dto.OrderPUTv1;
import com.FinalProject.exception.NotFoundException;
import com.FinalProject.mapper.OrderMapper;
import com.FinalProject.model.Order;
import com.FinalProject.repository.OrderRepo;
import com.FinalProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService<OrderGETv1, OrderPOSTv1, OrderPUTv1> {

    private final OrderRepo orderRepo;

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;


    // TODO: 4/11/2023 ADD Book service


    @Transactional
    @Override
    public OrderGETv1 add(OrderPOSTv1 dto) {

        Order order = orderMapper.toEntity(dto);

        order = orderRepo.saveAndFlush(order);

        // TODO: 4/11/2023 -1 from book stock

        return orderMapper.toGetDto(order);
    }

    @Override
    public OrderGETv1 get(Long ID) {

        var order = orderRepo.findById(ID);

        if(order.isEmpty()) throw new NotFoundException();

        return orderMapper.toGetDto(order.get());
    }

    @Override
    public OrderGETv1 update(OrderPUTv1 dto) {
        return null;
    }

    @Override
    public void delete(Long ID) {
        orderRepo.deleteById(ID);
    }


    @Transactional
    @Override
    public void disableProgress(Long ID) {
        orderRepo.disableProgress(ID,LocalDateTime.now());
        // TODO: 4/11/2023 +1 to book stock
    }
}
