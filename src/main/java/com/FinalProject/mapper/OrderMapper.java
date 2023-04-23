package com.FinalProject.mapper;

import com.FinalProject.dto.OrderGETv1;
import com.FinalProject.dto.OrderPOSTv1;
import com.FinalProject.model.Books;
import com.FinalProject.model.Order;
import com.FinalProject.model.Student;
import com.FinalProject.service.OrderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    default Order toEntity(OrderPOSTv1 dto){
        return Order.builder().student(Student.builder().ID(dto.getStudentId()).build())
                .books(dto.getBooks().stream().map(t-> Books.builder().id(t).build()).collect(Collectors.toSet()))
                .build();
    };

    @Mapping(target = "id",source = "ID")
    @Mapping(target = "studentId",source = "student.ID")
    @Mapping(target = "books",source = "books" ,qualifiedByName = "books")
    OrderGETv1 toGetDto(Order order);


    @Named("books")
    default List<Long> map(Set<Books> books){
        List<Long> temp = new LinkedList<>(books.stream().map(t->t.getId()).collect(Collectors.toList()));
        for (int i = 0; i < 15-books.size(); i++) {
            temp.add(null);
        }

        return temp;
    }



    default Order change(OrderPOSTv1 dto,Order entity){
        entity.setStudent(Student.builder().ID(dto.getStudentId()).build());
        entity.setBooks(entity.getBooks());
        return entity;
    }



}
