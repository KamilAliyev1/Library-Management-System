package com.FinalProject.exception;

import com.FinalProject.security.UserNotFound;
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
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            NotChangeableException.class,
            OrderNotFoundException.class,
            StockNotEnoughException.class,
            OrderStudentUniqueException.class,
            OrderMustUpdateException.class,
            HaveAlreadyBookException.class,
            BookAlreadyFoundException.class
    })
    public ResponseEntity<?> userExceptions(Exception userException) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userException.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> validationException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?> userNotFoundException(UserNotFound userNotFound) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userNotFound.getMessage());
    }

    @ExceptionHandler(BookAlreadyFoundException.class)
    public ResponseEntity<?> bookAlreadyFoundException(BookAlreadyFoundException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

//    @ExceptionHandler(TokenExpiredException.class)
//    public ResponseEntity<String> tokenExpiredException(HttpServletResponse response) {
//        Cookie cookie = new Cookie("jwt", null);
//        cookie.setMaxAge(0);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        return ResponseEntity.ok().body("/login");
//    }


//    @ExceptionHandler(IllegalArgumentException.class)
//    public String illegalArgumentException() {
//        return "register";
//    }


}