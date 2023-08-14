package com.FinalProject.mapper;

import com.FinalProject.dto.OrderDto;
import com.FinalProject.dto.OrderRequest;
import com.FinalProject.model.Book;
import com.FinalProject.model.Order;
import com.FinalProject.model.Student;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequest dto) {
        return Order
                .builder()
                .student(
                        Student
                                .builder()
                                .ID(
                                        dto.getStudentId()
                                ).build())
                .books(
                        dto
                                .getBooks()
                                .stream()
                                .filter(Objects::nonNull)
                                .map(t ->
                                        Book
                                                .builder()
                                                .id(t)
                                                .build()
                                )
                                .collect(Collectors.toSet()))
                .build();
    }

    public OrderDto toGetDto(Order order) {

        if (order == null) {
            return null;
        }

        Long id = order.getID();
        Long studentId = orderStudentID(order);
        List<String> books = order.getBooks().stream().map(Book::getName).collect(Collectors.toList());
        Boolean inProgress = order.getInProgress();
        LocalDate createdAt = order.getCreatedAt();
        LocalDate finishedAt = order.getFinishedAt();
        String studentFullName = order.getStudent().getName().concat(" ").concat(order.getStudent().getSurname());
        Boolean inDelay = order.getInDelay();

        return new OrderDto(id, studentId, studentFullName, books, inProgress, createdAt, finishedAt, inDelay);
    }

    public List<Long> map(Set<Book> books) {
        List<Long> temp = books == null || books.isEmpty() ? new LinkedList<>() : books.stream().map(Book::getId).collect(Collectors.toCollection(LinkedList::new));
        var size = temp.size();
        for (int i = 0; i < 15 - size; i++) {
            temp.add(null);
        }

        return temp;
    }

    private Long orderStudentID(Order order) {
        if (order == null) {
            return null;
        }
        Student student = order.getStudent();
        if (student == null) {
            return null;
        }
        Long iD = student.getID();
        if (iD == null) {
            return null;
        }
        return iD;
    }

    public Order setDtoChangesToEntity(OrderRequest dto, Order entity) {
        entity.setStudent(Student.builder().ID(dto.getStudentId()).build());
        entity.setBooks(dto.getBooks().stream().map(t -> Book.builder().id(t).build()).collect(Collectors.toSet()));
        return entity;
    }
}
