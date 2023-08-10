package com.FinalProject.service;

import com.FinalProject.dto.OrderDto;
import com.FinalProject.dto.OrderRequest;

import java.util.List;

public interface OrderService {

    void add(OrderRequest dto);

    OrderDto get(Long ID);

    OrderDto update(Long id, OrderRequest dto);

    void delete(Long ID);

    void disableProgress(Long ID);

    List<OrderDto> getAll();
}
