package com.FinalProject.exception;

public class NotDeletableException extends RuntimeException{
    public NotDeletableException() {
    }

    public NotDeletableException(String message) {
        super(message);
    }
}
