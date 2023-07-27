package com.FinalProject.exception;

public class HaveAlreadyBookException extends RuntimeException{
    public HaveAlreadyBookException() {
    }

    public HaveAlreadyBookException(String message) {
        super(message);
    }
}
