package com.FinalProject.exception;

public class EmailAlreadyFoundException extends RuntimeException {
    public EmailAlreadyFoundException(String message) {
        super(message);
    }
}
