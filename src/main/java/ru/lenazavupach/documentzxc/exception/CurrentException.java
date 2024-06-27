package ru.lenazavupach.documentzxc.exception;

import lombok.Getter;

@Getter
public class CurrentException extends RuntimeException{
    private final ErrorType type;

    public CurrentException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

    public CurrentException(ErrorType type, String message, Throwable throwable) {
        super(message, throwable);
        this.type = type;
    }

    public CurrentException(ErrorType type, Throwable throwable) {
        super(throwable);
        this.type = type;
    }
}