package com.FinalProject.exception;

import com.FinalProject.security.exception.EmailAlreadyFoundException;
import com.FinalProject.security.exception.UserNotFound;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({
            OrderNotFoundException.class
    })
    public ResponseEntity<?> userExceptions(Exception userException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userException.getMessage());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> validationException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String updateException(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        request.getSession().setAttribute("exception", "Book already found with isbn ");
        return "redirect:" + referer;
    }


    @ExceptionHandler({
            NotChangeableException.class,
            HaveAlreadyBookException.class,
            OrderMustUpdateException.class,
            StockNotEnoughException.class,
            NotDeletableException.class,
            AuthorsNotFoundException.class,
            CategoryNotFoundException.class,
            CategoryAlreadyExistsException.class,
            UsernameNotFoundException.class,
            UserNotFound.class,
            EmailAlreadyFoundException.class,
            AuthenticationException.class,
            StudentNotFoundException.class,
            StudentAlreadyExistsException.class,
            BookAlreadyFoundException.class,
            BookNotFoundException.class,
            IllegalArgumentException.class,
    })
    public String orderUpdateExceptions(Exception userException, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        request.getSession().setAttribute("exception", userException.getMessage());
        return "redirect:" + referer;
    }
}