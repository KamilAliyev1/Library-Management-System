package com.FinalProject.security.exception;

public class EmailAlreadyFoundException extends RuntimeException {
    public EmailAlreadyFoundException(String message) {
        super(message);
    }
}
