package io.wisoft.tutorial_backend.handler.exception.service;

public class LectureNotFoundException extends RuntimeException {
    public LectureNotFoundException(String message) {
        super(message);
    }
}
