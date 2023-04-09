package com.FinalProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorsNotFoundException.class)
    public ResponseEntity<?> sportHallNotFoundException(AuthorsNotFoundException authorsNotFoundException) {
        List<String> detail = new ArrayList<>();
        detail.add(authorsNotFoundException.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("Author not Found !", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}