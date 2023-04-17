package com.FinalProject.exception;

public class NotChangeableException extends RuntimeException{
    public NotChangeableException() {
    }

    public NotChangeableException(String message) {
        super(message);
    }
}
