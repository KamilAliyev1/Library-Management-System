package com.FinalProject.exception;

public class OrderMustUpdateException extends RuntimeException{
    public OrderMustUpdateException() {
    }

    public OrderMustUpdateException(String message) {
        super(message);
    }
}
