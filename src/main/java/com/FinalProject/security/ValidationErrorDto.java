package com.FinalProject.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ValidationErrorDto<T> {

    private String message;
    private T value;

}
