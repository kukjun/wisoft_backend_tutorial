package io.wisoft.tutorial_backend.handler.exception.service;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
        super();
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}
