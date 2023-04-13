package com.FinalProject.mapper;

import com.FinalProject.dto.OrderGETv1;
import com.FinalProject.dto.OrderPOSTv1;
import com.FinalProject.model.Order;
import com.FinalProject.service.OrderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toEntity(OrderPOSTv1 dto);

    @Mapping(target = "id",source = "ID")
    OrderGETv1 toGetDto(Order order);


}
