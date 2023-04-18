package com.FinalProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorsNotFoundException.class)
    public ResponseEntity<?> authorNotFound(AuthorsNotFoundException authorsNotFoundException) {
        List<String> detail = new ArrayList<>();
        detail.add(authorsNotFoundException.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("Author not Found !", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> categoryNotFound(CategoryNotFoundException categoryNotFoundException) {
        List<String> detail = new ArrayList<>();
        detail.add(categoryNotFoundException.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("Category not Found !", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<?> categoryAlreadyExists(CategoryAlreadyExistsException categoryAlreadyExistsException) {
        List<String> detail = new ArrayList<>();
        detail.add(categoryAlreadyExistsException.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("Category Already Exists !", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler({NotChangeableException.class, NotFoundException.class})
    public ResponseEntity<?> userExceptions(Exception userException){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(userException.getMessage());
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> validationException(BindException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getFieldError().getDefaultMessage());
    }


}