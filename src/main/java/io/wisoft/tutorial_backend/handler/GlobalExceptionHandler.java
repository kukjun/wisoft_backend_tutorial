package io.wisoft.tutorial_backend.handler;

import io.wisoft.tutorial_backend.handler.exception.interceptor.AuthenticationException;
import io.wisoft.tutorial_backend.handler.exception.service.*;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> failAuthentication(final AuthenticationException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> commentNotFound(final CommentNotFoundException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(LectureNotFoundException.class)
    public ResponseEntity<ErrorResponse> lectureNotFound(final LectureNotFoundException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> memberNotFound(final MemberNotFoundException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> postNotFound(final PostNotFoundException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> passwordMismatch(final PasswordMismatchException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(CurrentMemberMismatchException.class)
    public ResponseEntity<ErrorResponse> currentMemberMismatch(final CurrentMemberMismatchException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> dtoValidation(final MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error)-> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    private ErrorResponse generateErrorResponseWithMessage(String errorMessage) {
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.add(errorMessage);
        return errorResponse;
    }
}

@Getter
class ErrorResponse {
    final List<String> message;

    public ErrorResponse() {
        this(new ArrayList<>());
//        this.message = new ArrayList<>();
    }
    public ErrorResponse(List<String> message) {
        this.message = message;
    }

    void add(String message) {
        this.message.add(message);
    }
}

