package com.FinalProject.exception;

public class OrderStudentUniqueException extends RuntimeException{
    public OrderStudentUniqueException() {
    }

    public OrderStudentUniqueException(String message) {
        super(message);
    }
}
