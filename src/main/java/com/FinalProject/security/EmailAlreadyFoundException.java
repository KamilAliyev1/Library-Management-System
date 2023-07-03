package com.FinalProject.security;

public class EmailAlreadyFoundException extends RuntimeException {
    public EmailAlreadyFoundException(String message) {
        super(message);
    }
}
