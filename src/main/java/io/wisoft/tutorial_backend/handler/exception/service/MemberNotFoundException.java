package io.wisoft.tutorial_backend.handler.exception.service;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException() {
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
