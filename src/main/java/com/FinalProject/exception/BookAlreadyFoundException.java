package com.FinalProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BookAlreadyFoundException extends RuntimeException {
    public BookAlreadyFoundException(String message) {
        super(message);
    }
}
