package com.FinalProject.mapper;

import com.FinalProject.dto.OrderDto;
import com.FinalProject.dto.OrderRequest;
import com.FinalProject.model.Book;
import com.FinalProject.model.Order;
import com.FinalProject.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    default Order toEntity(OrderRequest dto){
        return Order.builder().student(Student.builder().ID(dto.getStudentId()).build())
                .books(dto.getBooks().stream().filter(Objects::nonNull).map(t-> Book.builder().id(t).build()).collect(Collectors.toSet()))
                .build();
    };

    @Mapping(target = "id",source = "ID")
    @Mapping(target = "studentId",source = "student.ID")
    @Mapping(target = "books",source = "books" ,qualifiedByName = "books")
    OrderDto toGetDto(Order order);


    @Named("books")
    default List<Long> map(Set<Book> books){
        List<Long> temp =  books==null || books.isEmpty()?new LinkedList():books.stream().map(Book::getId).collect(Collectors.toCollection(LinkedList::new));
        var size = temp.size();
        for (int i = 0; i < 15-size; i++) {
            temp.add(null);
        }

        return temp;
    }



    default Order change(OrderRequest dto, Order entity){
        entity.setStudent(Student.builder().ID(dto.getStudentId()).build());
        entity.setBooks(dto.getBooks().stream().map(t->Book.builder().id(t).build()).collect(Collectors.toSet()));
        return entity;
    }



}
