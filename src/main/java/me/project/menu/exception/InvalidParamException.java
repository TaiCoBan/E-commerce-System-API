package me.project.menu.exception;

import org.springframework.http.HttpStatus;

public class InvalidParamException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public InvalidParamException(String message) {
        super(message);
    }
}
