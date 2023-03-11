package io.wisoft.tutorial_backend.handler.exception.service;

public class CurrentMemberMismatchException extends RuntimeException{
    public CurrentMemberMismatchException() {
    }

    public CurrentMemberMismatchException(String message) {
        super(message);
    }
}
