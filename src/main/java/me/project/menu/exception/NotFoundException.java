package me.project.menu.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException{
    private HttpStatus status;
    private String message;
    public NotFoundException(String message) {
        super(message);
    }
}
