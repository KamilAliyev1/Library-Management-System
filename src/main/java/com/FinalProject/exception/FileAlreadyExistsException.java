package com.FinalProject.exception;

public class FileAlreadyExistsException extends RuntimeException {
    public FileAlreadyExistsException() {
    }

    public FileAlreadyExistsException(String file) {
        super(file);
    }
}
