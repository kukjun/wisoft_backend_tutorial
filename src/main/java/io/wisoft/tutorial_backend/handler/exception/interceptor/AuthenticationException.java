package io.wisoft.tutorial_backend.handler.exception.interceptor;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
