package ru.kpfu.itis.lobanov.exceptions;

public class PassportNotFoundException extends RuntimeException {
    public PassportNotFoundException() {
    }

    public PassportNotFoundException(String message) {
        super(message);
    }

    public PassportNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PassportNotFoundException(Throwable cause) {
        super(cause);
    }

    public PassportNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
