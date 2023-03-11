package io.wisoft.tutorial_backend.handler.exception.service;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
