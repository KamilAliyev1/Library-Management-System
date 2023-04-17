package com.FinalProject.exception;

public class BookAlreadyFoundException extends RuntimeException {
    public BookAlreadyFoundException(String message) {
        super(message);
    }
}
