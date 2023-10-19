package com.FinalProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)

public class BooksExistsWithThisCategoryException extends RuntimeException {
    public BooksExistsWithThisCategoryException(String message) {
        super(message);
    }
}
