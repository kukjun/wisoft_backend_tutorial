package io.wisoft.tutorial_backend.handler.exception.service;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
