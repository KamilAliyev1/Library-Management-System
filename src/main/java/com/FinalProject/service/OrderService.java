package com.FinalProject.service;

import com.FinalProject.dto.OrderGETv1;
import com.FinalProject.dto.OrderPOSTv1;

import java.util.List;

public interface OrderService {

    OrderGETv1 add(OrderPOSTv1 dto);

    OrderGETv1 get(Long ID);

    OrderGETv1 update(Long id,OrderPOSTv1 dto);

    void delete(Long ID);

    void disableProgress(Long ID);

    List<OrderGETv1> getAll();

}
