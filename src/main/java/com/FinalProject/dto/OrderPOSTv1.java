package com.FinalProject.dto;

import com.FinalProject.model.Books;
import com.FinalProject.model.Student;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.print.Book;
import java.util.LinkedList;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPOSTv1{
        @NotNull
        public Long studentId;
        @NotEmpty
        public List<Long> books = new LinkedList<>();


}
