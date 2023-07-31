package com.FinalProject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequest {
        @NotNull
        public Long studentId;
        @NotEmpty
        public List<Long> books = new LinkedList<>();


}
